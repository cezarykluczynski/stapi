package com.cezarykluczynski.stapi.etl.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.configuration.IntervalCalculationStrategy;
import com.cezarykluczynski.stapi.etl.mediawiki.configuration.MediaWikiMinimalIntervalProvider;
import com.cezarykluczynski.stapi.etl.mediawiki.configuration.MediaWikiSourcesProperties;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.etl.mediawiki.service.fandom.FandomWikisDetector;
import com.cezarykluczynski.stapi.etl.mediawiki.util.constant.ApiParams;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import info.bliki.api.Connector;
import info.bliki.api.User;
import info.bliki.api.query.RequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Service
@SuppressWarnings("ClassFanOutComplexity")
@Slf4j
public class BlikiConnector {

	private static final List<Long> NETWORK_TROUBLE_POSTPONES_TIMES = Lists.newArrayList(10000L, 30000L, 60000L);

	private final Map<MediaWikiSource, Long> lastCallTimes = Maps.newHashMap();

	private final Map<MediaWikiSource, String> names = Maps.newHashMap();

	private final Map<MediaWikiSource, Boolean> logPostpones = Maps.newHashMap();

	private final Map<MediaWikiSource, IntervalCalculationStrategy> intervalCalculationStrategies = Maps.newHashMap();

	private Integer lastNetworkTroublePostponeIndex = 0;

	private final BlikiUserDecoratorFactory blikiUserDecoratorFactory;

	private final FandomWikisDetector fandomWikisDetector;

	private final MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProvider;

	private final MediaWikiSourcesProperties mediaWikiSourcesProperties;

	private final RestTemplate restTemplate;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public BlikiConnector(BlikiUserDecoratorFactory blikiUserDecoratorFactory, FandomWikisDetector fandomWikisDetector,
			MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProvider, MediaWikiSourcesProperties mediaWikiSourcesProperties,
			RestTemplate restTemplate) {
		this.blikiUserDecoratorFactory = blikiUserDecoratorFactory;
		this.fandomWikisDetector = fandomWikisDetector;
		this.mediaWikiMinimalIntervalProvider = mediaWikiMinimalIntervalProvider;
		this.mediaWikiSourcesProperties = mediaWikiSourcesProperties;
		this.restTemplate = restTemplate;
		configure();
	}

	private void configure() {
		lastCallTimes.put(MediaWikiSource.MEMORY_ALPHA_EN, 0L);
		lastCallTimes.put(MediaWikiSource.MEMORY_BETA_EN, 0L);
		names.put(MediaWikiSource.MEMORY_ALPHA_EN, "Memory Alpha (EN)");
		names.put(MediaWikiSource.MEMORY_BETA_EN, "Memory Beta (EN)");
		logPostpones.put(MediaWikiSource.MEMORY_ALPHA_EN, mediaWikiSourcesProperties.getMemoryAlphaEn().getLogPostpones());
		logPostpones.put(MediaWikiSource.MEMORY_BETA_EN, mediaWikiSourcesProperties.getMemoryBetaEn().getLogPostpones());
		intervalCalculationStrategies.put(MediaWikiSource.MEMORY_ALPHA_EN, mediaWikiSourcesProperties
				.getMemoryAlphaEn().getIntervalCalculationStrategy());
		intervalCalculationStrategies.put(MediaWikiSource.MEMORY_BETA_EN, mediaWikiSourcesProperties
				.getMemoryBetaEn().getIntervalCalculationStrategy());
	}

	@Cacheable(cacheNames = "pagesCache", condition = "@pageCacheService.isCacheable(#title, #mediaWikiSource)",
			key = "@pageCacheService.resolveKey(#title)", sync = true)
	public String getPage(String title, MediaWikiSource mediaWikiSource) {
		Map<String, String> params = Maps.newHashMap();
		params.put(ApiParams.KEY_ACTION, ApiParams.KEY_ACTION_VALUE_PARSE);
		params.put(ApiParams.KEY_PAGE, title);
		params.put(ApiParams.KEY_PROP, resolveProperties(mediaWikiSource));
		return readXML(params, mediaWikiSource);
	}

	public String getPageInfo(String title, MediaWikiSource mediaWikiSource) {
		Map<String, String> params = Maps.newHashMap();
		params.put(ApiParams.KEY_ACTION, ApiParams.KEY_ACTION_VALUE_QUERY);
		params.put(ApiParams.KEY_TITLES, title);
		return readXML(params, mediaWikiSource);
	}

	public String readXML(Map<String, String> params, MediaWikiSource mediaWikiSource) {
		RequestBuilder requestBuilder = RequestBuilder.create();
		requestBuilder.putAll(params);

		switch (mediaWikiSource) {
			case MEMORY_BETA_EN:
				return doQueryMemoryBeta(() -> doQuery(requestBuilder, MediaWikiSource.MEMORY_BETA_EN));
			default:
			case MEMORY_ALPHA_EN:
				return doQueryMemoryAlpha(() -> doQuery(requestBuilder, MediaWikiSource.MEMORY_ALPHA_EN));
		}
	}

	public List<CategoryHeader> getCategories(String categoryTitle, MediaWikiSource mediaWikiSource, int depth) {
		String rawCategoriesJson;
		switch (mediaWikiSource) {
			case MEMORY_BETA_EN:
				rawCategoriesJson = doQueryMemoryBeta(() -> rawJsonCategories(categoryTitle, depth, MediaWikiSource.MEMORY_BETA_EN));
				break;
			default:
			case MEMORY_ALPHA_EN:
				rawCategoriesJson = doQueryMemoryAlpha(() -> rawJsonCategories(categoryTitle, depth, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return rawJsonCategoriesToList(rawCategoriesJson);
	}

	public String getRawTemplateTransclusions(String templateName, String tiContinue, MediaWikiSource mediaWikiSource) {
		String jsonTransclusions;
		switch (mediaWikiSource) {
			case MEMORY_BETA_EN:
				jsonTransclusions = doQueryMemoryBeta(() -> rawTranscludedIn(templateName, tiContinue, MediaWikiSource.MEMORY_BETA_EN));
				break;
			default:
			case MEMORY_ALPHA_EN:
				jsonTransclusions = doQueryMemoryAlpha(() -> rawTranscludedIn(templateName, tiContinue, MediaWikiSource.MEMORY_ALPHA_EN));
				break;
		}
		return jsonTransclusions;
	}

	private String rawJsonCategories(String categoryTitle, int depth, MediaWikiSource mediaWikiSource) {
		String urlPrefix;
		if (MediaWikiSource.MEMORY_BETA_EN.equals(mediaWikiSource)) {
			urlPrefix = mediaWikiSourcesProperties.getMemoryBetaEn().getApiUrl();
		} else {
			urlPrefix = mediaWikiSourcesProperties.getMemoryAlphaEn().getApiUrl();
		}
		String options = String.format("{\"depth\":%d}", depth);
		String url = String.format("%s?action=categorytree&category=%s&format=json&options={options}", urlPrefix, categoryTitle);
		final ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class, options);
		return forEntity.getBody();
	}

	private String rawTranscludedIn(String templateTitle, String ticontinue, MediaWikiSource mediaWikiSource) {
		String urlPrefix;
		if (MediaWikiSource.MEMORY_BETA_EN.equals(mediaWikiSource)) {
			urlPrefix = mediaWikiSourcesProperties.getMemoryBetaEn().getApiUrl();
		} else {
			urlPrefix = mediaWikiSourcesProperties.getMemoryAlphaEn().getApiUrl();
		}
		String tiContinueParam = StringUtils.isNotBlank(ticontinue) ? "&ticontinue=" + ticontinue : "";
		String url = String.format("%s?action=query&prop=transcludedin&tilimit=500&tinamespace=0&titles=Template:%s&format=json%s",
				urlPrefix, templateTitle, tiContinueParam);
		final ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
		return forEntity.getBody();
	}

	private List<CategoryHeader> rawJsonCategoriesToList(String rawJsonCategories) {
		JSONObject jsonObject = new JSONObject(rawJsonCategories);
		final JSONObject categorytree = jsonObject.getJSONObject("categorytree");
		List<CategoryHeader> categoryHeaders = Lists.newArrayList();
		for (String sortKey : categorytree.keySet()) {
			final Document document = Jsoup.parse(categorytree.getString(sortKey));
			final Elements categoryTreeSection = document.getElementsByClass("CategoryTreeSection");
			for (Element element : categoryTreeSection) {
				final Elements a = element.select("div a");
				if (a.size() == 0) {
					log.warn("For category tree {} found no links.", element);
					continue;
				}
				final Element first = a.first();
				final String title = first.attr("title");
				if (title.startsWith("Category:")) {
					final String categoryTitle = title.substring(9);
					CategoryHeader categoryHeader = new CategoryHeader();
					categoryHeader.setTitle(categoryTitle.replace(" ", "_"));
					categoryHeaders.add(categoryHeader);
				} else {
					log.info("Title {} does not appear to be a category.", title);
				}
			}
		}
		return categoryHeaders;
	}

	private synchronized String doQueryMemoryAlpha(Supplier<String> supplier) {
		return synchronizedDoQuery(MediaWikiSource.MEMORY_ALPHA_EN,
				mediaWikiMinimalIntervalProvider.getMemoryAlphaEnInterval(), supplier);
	}

	private synchronized String doQueryMemoryBeta(Supplier<String> supplier) {
		return synchronizedDoQuery(MediaWikiSource.MEMORY_BETA_EN,
				mediaWikiMinimalIntervalProvider.getMemoryBetaEnInterval(), supplier);
	}

	@SuppressWarnings("NPathComplexity")
	@SuppressFBWarnings("SWL_SLEEP_WITH_LOCK_HELD")
	private String synchronizedDoQuery(MediaWikiSource mediaWikiSource, Long interval, Supplier<String> supplier) {
		long startTime = System.currentTimeMillis();
		long diff = startTime - lastCallTimes.get(mediaWikiSource);
		long minimalInterval = interval;

		if (diff < minimalInterval) {
			long postpone = minimalInterval - diff;
			if (BooleanUtils.isTrue(logPostpones.get(mediaWikiSource))) {
				log.info("Postponing call to {} for another {} milliseconds", names.get(mediaWikiSource), postpone);
			}
			try {
				Thread.sleep(postpone);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		String result = null;
		boolean finishedWithException;
		do {
			try {
				result = supplier.get();
				finishedWithException = false;
			} catch (FutureException futureException) {
				finishedWithException = true;
				log.info("Future for source {} ended with exception, retrying.", mediaWikiSource);
			}
		} while (finishedWithException);

		long lastCallTime = IntervalCalculationStrategy.FROM_AFTER_RECEIVED
				.equals(intervalCalculationStrategies.get(mediaWikiSource)) ? System.currentTimeMillis() : startTime;
		lastCallTimes.put(mediaWikiSource, lastCallTime);

		synchronized (this) {
			if (result == null) {
				long postpone = getNetworkTroublePostpone();
				log.info("Network troubles. Postponing next call for {} seconds", postpone / 1000);
				try {
					Thread.sleep(postpone);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				return synchronizedDoQuery(mediaWikiSource, interval, supplier);
			} else {
				if (lastNetworkTroublePostponeIndex > 0) {
					log.info("Network is back to normal");
				}
				lastNetworkTroublePostponeIndex = 0;
			}
		}

		return result;
	}

	private String doQuery(RequestBuilder requestBuilder, MediaWikiSource mediaWikiSource) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Callable<Object> task = () -> doQueryUnwrapped(requestBuilder, mediaWikiSource);
		Future<Object> future = executor.submit(task);
		try {
			// due to some hard to diagnose problems in JDK, that causes HTTP-related executions to hang indefinitely,
			// let's try to mitigate with a Future with a timeout.
			return (String) future.get(60, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			throw new FutureException(e);
		} catch (ExecutionException | InterruptedException e) {
			throw new StapiRuntimeException(e);
		} finally {
			future.cancel(true);
		}
	}

	private String doQueryUnwrapped(RequestBuilder requestBuilder, MediaWikiSource mediaWikiSource) {
		// TODO: remove private method call once this PR is merged and published in Maven Central
		// https://bitbucket.org/axelclk/info.bliki.wiki/pull-requests/10/connector-sendxml-should-be-public/diff
		try {
			UserDecorator userDecorator = blikiUserDecoratorFactory.createFor(mediaWikiSource);
			Connector connector = userDecorator.getConnector();
			Method method = connector.getClass().getDeclaredMethod("sendXML", User.class, RequestBuilder.class);
			method.setAccessible(true);
			return (String) method.invoke(connector, userDecorator, requestBuilder);
		} catch (Exception e) {
			throw new StapiRuntimeException(e);
		}
	}

	private String resolveProperties(MediaWikiSource mediaWikiSource) {
		return fandomWikisDetector.isFandomWiki(mediaWikiSource) ? ApiParams.KEY_PROP_VALUE : ApiParams.KEY_PROP_VALUE_MW_1_26_UP;
	}

	private long getNetworkTroublePostpone() {
		long networkTroublePostpone = NETWORK_TROUBLE_POSTPONES_TIMES.get(lastNetworkTroublePostponeIndex);
		lastNetworkTroublePostponeIndex = Math.min(lastNetworkTroublePostponeIndex + 1, NETWORK_TROUBLE_POSTPONES_TIMES.size() - 1);
		return networkTroublePostpone;
	}

	static class FutureException extends RuntimeException {

		FutureException(Throwable cause) {
			super(cause);
		}

	}

}
