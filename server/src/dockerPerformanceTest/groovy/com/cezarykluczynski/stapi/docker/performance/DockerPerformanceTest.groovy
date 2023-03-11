package com.cezarykluczynski.stapi.docker.performance

import static org.codehaus.groovy.runtime.StringGroovyMethods.padLeft
import static org.codehaus.groovy.runtime.StringGroovyMethods.padRight

import com.cezarykluczynski.stapi.client.api.StapiRestClient
import com.cezarykluczynski.stapi.client.api.dto.AbstractPageSortBaseCriteria
import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.google.common.base.Stopwatch
import com.google.common.collect.Lists
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.retry.backoff.NoBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.retry.support.RetryTemplateBuilder
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import java.lang.reflect.Method
import java.time.Duration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors

@Testcontainers
class DockerPerformanceTest extends Specification {

	private static GenericContainer stapiContainer

	private static StapiRestClient stapiRestClient

	private static RetryTemplate retryTemplate = new RetryTemplateBuilder()
			.customPolicy(new SimpleRetryPolicy(5))
			.customBackoff(new NoBackOffPolicy())
			.build()

	void setup() {
		if (stapiContainer == null) {
			stapiContainer = new GenericContainer<>("cezarykluczynski/stapi:latest")
					.withExposedPorts(8686)
					.withLogConsumer(new Slf4jLogConsumer(LOG))
					.waitingFor(new HttpWaitStrategy().forPath("/api/v1/rest/common/ping").withStartupTimeout(Duration.ofMinutes(3L)))
			stapiContainer.start()
			String address = stapiContainer.host
			Integer port = stapiContainer.firstMappedPort
			stapiRestClient = new StapiRestClient("http://" + address + ":" + port + "/")
		}
	}

	void "checks all endpoints"() {
		given:
		Stopwatch stopwatch = Stopwatch.createStarted()
		def fields = stapiRestClient.getClass().getDeclaredFields()
		def apis = []
		for (def field : fields) {
			field.setAccessible(true)
			Object api = field.get(stapiRestClient)
			def methods = api.getClass().getDeclaredMethods()
			if (methods.any { it.name.startsWith('search') } && methods.any { it.name.startsWith('get') }) {
				apis.add(api)
			}
		}
		List<TestContext> contexts = []
		for (Object api : apis) {
			def methods = api.class.getDeclaredMethods()
			Method searchMethod
			Method getMethod
			for (def method : methods) {
				if (method.getAnnotation(Deprecated) != null) {
					continue
				}
				if (method.name.startsWith('search') && method.parameterCount == 1) {
					searchMethod = method
				}
				if (method.name.startsWith('get') && method.parameterCount == 1) {
					getMethod = method
				}
			}
			if (searchMethod == null) {
				throw new RuntimeException("Not search method for api!")
			}
			if (getMethod == null) {
				throw new RuntimeException("Not get method for api!")
			}

			def criteria = Class.forName(searchMethod.parameterTypes[0].name).getConstructors()[0].newInstance()
			String name = api.getClass().getSimpleName()
			if (!(criteria instanceof AbstractPageSortBaseCriteria)) {
				throw new RuntimeException("Criteria not an instance of AbstractPageSortBaseCriteria for $name")
			} else {
				criteria.setPageNumber(0)
				criteria.setPageSize(100)
			}
			TestContext testContext = new TestContext()
			testContext.name = name
			testContext.api = api
			testContext.searchMethod = searchMethod
			testContext.getMethod = getMethod
			testContext.criteria = (AbstractPageSortBaseCriteria) criteria
			testContext
			contexts.add(testContext)
		}
		for (TestContext testContext : contexts) {
			Stopwatch stopwatchGets = Stopwatch.createStarted()
			def criteria = testContext.getCriteria()
			boolean hasNext = true
			def results = []
			while (hasNext) {
				Object baseResponse
				try {
					retryTemplate.execute {
						baseResponse = testContext.searchMethod.invoke(testContext.api, criteria)
					}
				} catch (Exception e) {
					throw new RuntimeException(e)
				}
				def baseResponseFields = baseResponse.getClass().getDeclaredFields()
				ResponsePage responsePage
				for (def field : baseResponseFields) {
					field.setAccessible(true)
					Object candidate = field.get(baseResponse)
					if (field.name == 'page') {
						responsePage = (ResponsePage) candidate
						hasNext = !responsePage.lastPage
					}
					if (List.isAssignableFrom(candidate.class)) {
						results.addAll((List) candidate)
					}
				}
				criteria.setPageNumber(criteria.pageNumber + 1)
			}
			testContext.results = results
			stopwatchGets.stop()
			testContext.searchElapsedSeconds = stopwatchGets.elapsed(TimeUnit.SECONDS)
			println("Searching entities took ${testContext.searchElapsedSeconds} s. for entity $testContext.name.")
		}
		for (TestContext testContext : contexts) {
			int numberOfCoresToUse = (int) (Runtime.getRuntime().availableProcessors() - 2) / 2
			ExecutorService executorService = Executors.newFixedThreadPool(Math.max(1, numberOfCoresToUse))
			AtomicInteger errors = new AtomicInteger()
			AtomicInteger successes = new AtomicInteger()
			List<Long> getCallTimes = Collections.synchronizedList(Lists.newArrayList())
			List<String> failures = Collections.synchronizedList(Lists.newArrayList())
			Stopwatch stopwatchEntity = Stopwatch.createStarted()
			for (Object result : testContext.results) {
				def field = result.getClass().getDeclaredField('uid')
				field.setAccessible(true)
				final String uid = field.get(result)
				executorService.submit {
					Stopwatch stopwatchSingleGet = Stopwatch.createStarted()
					try {
						retryTemplate.execute {
							try {
								def object = testContext.getMethod.invoke(testContext.api, uid)
								if (object == null) {
									throw new RuntimeException()
								}
							} catch (Exception e) {
								println("Failed to get ${testContext.name} with id: $uid")
								errors.incrementAndGet()
								throw e
							}
						}
					} catch (Exception e) {
						failures.add(uid)
					}
					successes.incrementAndGet()
					stopwatchSingleGet.stop()
					getCallTimes.add(stopwatchSingleGet.elapsed(TimeUnit.SECONDS))
				}
			}
			executorService.shutdown()
			executorService.awaitTermination(60L, TimeUnit.MINUTES)
			stopwatchEntity.stop()
			testContext.getElapsedSeconds = stopwatchEntity.elapsed(TimeUnit.SECONDS)
			testContext.getErrors = errors.get()
			testContext.getMaxExecutionTime = getCallTimes.stream().mapToInt { it -> (int) it }.max().getAsInt()
			testContext.failures = failures
			println("Getting entities took ${testContext.getElapsedSeconds} s. for entity $testContext.name.")
		}
		List<TestContext> problematicContexts = contexts.stream()
				.filter { it.getErrors > 0 || it.failures.size() > 0 || it.getMaxExecutionTime > 8 }
				.toList()
		List<TestContext> failingContexts = contexts.stream()
				.filter { it.failures.size() > 0 }
				.toList()
		println("Performance tests took a total of ${stopwatch.elapsed(TimeUnit.MINUTES)} minutes.")

		expect:
		report(problematicContexts, true)
		report(contexts, false)
		failingContexts.empty
	}

	private static void report(List<TestContext> testContexts, boolean problematic) {
		if (testContexts.empty) {
			if (problematic) {
				println('No performance problems found.')
			}
			return
		}
		if (problematic) {
			println('Found performance problems are:')
		} else {
			println('All performance results are:')

		}
		String entityHeader = 'Entity'
		String entityCountHeader = 'Entity count'
		String errorsHeader = 'Errors'
		String failuresHeader = 'Failures'
		String getMaxExecutionTimeHeader = 'GET max exec time (s.)'
		String exampleErrorUidsHeader = 'Example failing UIDs'
		String template = '%s | %s | %s | %s | %s | %s'
		Integer entityMaxLength = testContexts.stream().mapToInt() { it.name.length() }.max().getAsInt()
		Integer entityCountMaxLength = entityCountHeader.length()
		Integer errorsMaxLength = errorsHeader.length()
		Integer failuresMaxLength = failuresHeader.length()
		Integer getMaxExecutionTimeMaxLength = getMaxExecutionTimeHeader.length()
		List<String> results = [String.format(template,
				padRight(entityHeader, entityMaxLength),
				entityCountHeader,
				errorsHeader,
				failuresHeader,
				getMaxExecutionTimeHeader,
				exampleErrorUidsHeader)]
		for (TestContext testContext : testContexts) {
			results.add(String.format(template,
					padRight(testContext.name, entityMaxLength),
					padLeft("${testContext.results.size()}", entityCountMaxLength),
					padLeft("${testContext.getErrors}", errorsMaxLength),
					padLeft("${testContext.failures.size()}", failuresMaxLength),
					padLeft("${testContext.getMaxExecutionTime}", getMaxExecutionTimeMaxLength),
					maxItems(testContext.failures, 3)
			))
		}
		results.forEach { println(it) }
	}

	private static final String maxItems(List<String> items, int max) {
		if (items.empty) {
			return '-'
		}
		List<String> trimmedList = items.size() > max ? items.subList(0, max) : items
		return trimmedList.stream().collect(Collectors.joining(", "))
	}

	static class TestContext {

		String name
		Object api
		Method searchMethod
		Method getMethod
		AbstractPageSortBaseCriteria criteria
		List results
		List<String> failures
		Long searchElapsedSeconds
		Long getElapsedSeconds
		Integer getErrors
		Integer getMaxExecutionTime

	}

}
