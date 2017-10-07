package com.cezarykluczynski.stapi.server.common.throttle

import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties
import com.cezarykluczynski.stapi.server.common.service.NowProvider
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredential
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialType
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.google.common.collect.Sets
import spock.lang.Specification

import java.time.LocalDateTime
import java.util.concurrent.ExecutionException

class FrequentRequestsValidatorTest extends Specification {

	private static final String API_KEY = 'API_KEY'
	private static final int FREQUENT_REQUESTS_PERIOD_LENGTH_IN_SECONDS = 5
	private static final int MAX_REQUESTS_PER_PERIOD = 2

	private ThrottleProperties throttlePropertiesMock

	private FrequentRequestsCacheBuilder frequentRequestsCacheBuilderMock

	private FrequentRequestsKeysFactory frequentRequestsKeysFactoryMock

	private NowProvider nowProviderMock

	private FrequentRequestsValidator frequentRequestsValidator

	void setup() {
		throttlePropertiesMock = Mock()
		frequentRequestsCacheBuilderMock = Mock()
		frequentRequestsKeysFactoryMock = Mock()
		nowProviderMock = Mock()
		frequentRequestsValidator = new FrequentRequestsValidator(throttlePropertiesMock, frequentRequestsCacheBuilderMock,
				frequentRequestsKeysFactoryMock, nowProviderMock)
	}

	void "starts throttling when there is too many request in a given period"() {
		given:
		Cache<FrequentRequestsKey, List<LocalDateTime>> cache = CacheBuilder.newBuilder().build()
		RequestCredential requestCredential = Mock()
		Set<FrequentRequestsKey> frequentRequestsKeySet = Sets.newHashSet(FrequentRequestsKey.of(RequestCredentialType.API_KEY, API_KEY))
		LocalDateTime now = LocalDateTime.of(2017, 10, 7, 9, 48, 00)

		when:
		frequentRequestsValidator.postConstruct()

		then:
		1 * frequentRequestsCacheBuilderMock.build() >> cache
		0 * _

		when:
		ThrottleResult throttleResultAfterFirstRequest = frequentRequestsValidator.validate(requestCredential)

		then:
		1 * frequentRequestsKeysFactoryMock.create(requestCredential) >> frequentRequestsKeySet
		1 * nowProviderMock.now() >> now
		1 * throttlePropertiesMock.frequentRequestsPeriodLengthInSeconds >> FREQUENT_REQUESTS_PERIOD_LENGTH_IN_SECONDS
		1 * throttlePropertiesMock.frequentRequestsMaxRequestsPerPeriod >> MAX_REQUESTS_PER_PERIOD
		0 * _
		!throttleResultAfterFirstRequest.throttle

		when:
		ThrottleResult throttleResultAfterSecondRequest = frequentRequestsValidator.validate(requestCredential)

		then:
		1 * frequentRequestsKeysFactoryMock.create(requestCredential) >> frequentRequestsKeySet
		1 * nowProviderMock.now() >> now
		1 * throttlePropertiesMock.frequentRequestsPeriodLengthInSeconds >> FREQUENT_REQUESTS_PERIOD_LENGTH_IN_SECONDS
		1 * throttlePropertiesMock.frequentRequestsMaxRequestsPerPeriod >> MAX_REQUESTS_PER_PERIOD
		0 * _
		!throttleResultAfterSecondRequest.throttle

		when:
		ThrottleResult throttleResultAfterThirdRequest = frequentRequestsValidator.validate(requestCredential)

		then:
		1 * frequentRequestsKeysFactoryMock.create(requestCredential) >> frequentRequestsKeySet
		1 * nowProviderMock.now() >> now
		1 * throttlePropertiesMock.frequentRequestsPeriodLengthInSeconds >> FREQUENT_REQUESTS_PERIOD_LENGTH_IN_SECONDS
		1 * throttlePropertiesMock.frequentRequestsMaxRequestsPerPeriod >> MAX_REQUESTS_PER_PERIOD
		0 * _
		throttleResultAfterThirdRequest.throttle
		throttleResultAfterThirdRequest.throttleReason == ThrottleReason.TOO_SHORT_INTERVAL_BETWEEN_REQUESTS
	}

	void "ExecutionException is rethrown as RuntimeException"() {
		given:
		Cache<FrequentRequestsKey, List<LocalDateTime>> cache = Mock()
		RequestCredential requestCredential = Mock()
		Set<FrequentRequestsKey> frequentRequestsKeySet = Sets.newHashSet(FrequentRequestsKey.of(RequestCredentialType.API_KEY, API_KEY))
		LocalDateTime now = LocalDateTime.of(2017, 10, 7, 9, 48, 00)

		when:
		frequentRequestsValidator.postConstruct()

		then:
		1 * frequentRequestsCacheBuilderMock.build() >> cache
		0 * _

		when:
		frequentRequestsValidator.validate(requestCredential)

		then:
		1 * frequentRequestsKeysFactoryMock.create(requestCredential) >> frequentRequestsKeySet
		1 * nowProviderMock.now() >> now
		1 * throttlePropertiesMock.frequentRequestsPeriodLengthInSeconds >> FREQUENT_REQUESTS_PERIOD_LENGTH_IN_SECONDS
		1 * cache.get(_, _) >> { FrequentRequestsKey frequentRequestsKey ->
			throw new ExecutionException()
		}
		0 * _
		thrown(RuntimeException)
	}

}
