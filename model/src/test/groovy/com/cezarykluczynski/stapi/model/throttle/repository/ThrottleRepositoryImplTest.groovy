package com.cezarykluczynski.stapi.model.throttle.repository

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey_
import com.cezarykluczynski.stapi.model.api_key.query.ApiKeyQueryBuilderFactory
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties
import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle
import com.cezarykluczynski.stapi.model.throttle.entity.enums.ThrottleType
import com.google.common.collect.Lists
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId

class ThrottleRepositoryImplTest extends Specification {

	private static final String IP_ADDRESS = 'IP_ADDRESS'
	private static final String API_KEY = 'API_KEY'
	private static final Integer IP_ADDRESS_HOURLY_LIMIT = 100
	private static final Integer API_KEY_HOURLY_LIMIT = 10000
	private static final Integer IP_ADDRESS_HOURLY_LIMIT_DECREMENTED = 99
	private static final Integer REMAINING_HITS = 10
	private static final Integer REMAINING_HITS_DECREMENTED = 9
	private static final Integer MINUTES_TO_DELETE_EXPIRED_IP_ADDRESSES = 1440
	private static final Integer API_KEY_1_LIMIT = 1000
	private static final Integer API_KEY_2_LIMIT = 2000

	private ThrottleProperties throttlePropertiesMock

	private ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactoryMock

	private ThrottleRepository throttleRepositoryMock

	private ApiKeyRepository apiKeyRepositoryMock

	private ThrottleRepositoryImpl throttleRepositoryImpl

	void setup() {
		throttlePropertiesMock = Mock()
		apiKeyQueryBuilderFactoryMock = Mock()
		throttleRepositoryMock = Mock()
		apiKeyRepositoryMock = Mock()
		throttleRepositoryImpl = new ThrottleRepositoryImpl(throttlePropertiesMock, apiKeyQueryBuilderFactoryMock)
		throttleRepositoryImpl.throttleRepository = throttleRepositoryMock
		throttleRepositoryImpl.apiKeyRepository = apiKeyRepositoryMock
	}

	void "decrements existing IP and returns statistics marked as successful"() {
		given:
		Throttle throttle = new Throttle(remainingHits: REMAINING_HITS)

		when:
		ThrottleStatistics throttleStatistics = throttleRepositoryImpl.decrementByIpAndGetStatistics(IP_ADDRESS)

		then:
		1 * throttlePropertiesMock.ipAddressHourlyLimit >> IP_ADDRESS_HOURLY_LIMIT
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.of(throttle)
		1 * throttleRepositoryMock.decrementByIp(IP_ADDRESS, _)
		0 * _
		throttleStatistics.decremented
		throttleStatistics.total == IP_ADDRESS_HOURLY_LIMIT
		throttleStatistics.remaining == REMAINING_HITS_DECREMENTED
	}

	void "decrements new IP and returns statistics marked as successful"() {
		when:
		ThrottleStatistics throttleStatistics = throttleRepositoryImpl.decrementByIpAndGetStatistics(IP_ADDRESS)

		then:
		1 * throttlePropertiesMock.ipAddressHourlyLimit >> IP_ADDRESS_HOURLY_LIMIT
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.empty()
		1 * throttleRepositoryMock.save(_ as Throttle) >> { Throttle throttle ->
			assert throttle.throttleType == ThrottleType.IP_ADDRESS
			assert throttle.ipAddress == IP_ADDRESS
			assert throttle.hitsLimit == IP_ADDRESS_HOURLY_LIMIT
			assert throttle.remainingHits == IP_ADDRESS_HOURLY_LIMIT - 1
			assert throttle.lastHitTime != null
		}
		0 * _
		throttleStatistics.decremented
		throttleStatistics.total == IP_ADDRESS_HOURLY_LIMIT
		throttleStatistics.remaining == IP_ADDRESS_HOURLY_LIMIT_DECREMENTED
	}

	void "decrements existing IP when save throws DataIntegrityViolationException, then returns statistics marked as successful"() {
		when:
		ThrottleStatistics throttleStatistics = throttleRepositoryImpl.decrementByIpAndGetStatistics(IP_ADDRESS)

		then:
		1 * throttlePropertiesMock.ipAddressHourlyLimit >> IP_ADDRESS_HOURLY_LIMIT
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.empty()
		1 * throttleRepositoryMock.save(_ as Throttle) >> { Throttle throttle ->
			throw new DataIntegrityViolationException('')
		}
		1 * throttleRepositoryMock.decrementByIp(IP_ADDRESS, _)
		0 * _
		throttleStatistics.decremented
		throttleStatistics.total == IP_ADDRESS_HOURLY_LIMIT
		throttleStatistics.remaining == IP_ADDRESS_HOURLY_LIMIT_DECREMENTED
	}

	void "when decrementing by IP, returns statistics marked as not successful when there are 0 remaining hits"() {
		given:
		Throttle throttle = new Throttle(remainingHits: 0)

		when:
		ThrottleStatistics throttleStatistics = throttleRepositoryImpl.decrementByIpAndGetStatistics(IP_ADDRESS)

		then:
		1 * throttlePropertiesMock.ipAddressHourlyLimit >> IP_ADDRESS_HOURLY_LIMIT
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.of(throttle)
		0 * _
		!throttleStatistics.decremented
		throttleStatistics.total == IP_ADDRESS_HOURLY_LIMIT
		throttleStatistics.remaining == 0
	}

	@SuppressWarnings('BracesForMethod')
	void """when decrementing by IP, returns statistics marked as not successful when there are less than 0 remaining hits,
			and sets remaining hits to 0"""() {
		given:
		Throttle throttle = new Throttle(remainingHits: -1)

		when:
		ThrottleStatistics throttleStatistics = throttleRepositoryImpl.decrementByIpAndGetStatistics(IP_ADDRESS)

		then:
		1 * throttlePropertiesMock.ipAddressHourlyLimit >> IP_ADDRESS_HOURLY_LIMIT
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.of(throttle)
		0 * _
		!throttleStatistics.decremented
		throttleStatistics.total == IP_ADDRESS_HOURLY_LIMIT
		throttleStatistics.remaining == 0
	}

	void "decrements existing API key and returns statistics marked as successful"() {
		given:
		Throttle throttle = new Throttle(remainingHits: REMAINING_HITS)
		ApiKey apiKey = new ApiKey(limit: API_KEY_HOURLY_LIMIT)

		when:
		ThrottleStatistics throttleStatistics = throttleRepositoryImpl.decrementByApiKeyAndGetStatistics(API_KEY, IP_ADDRESS)

		then:
		1 * apiKeyRepositoryMock.findByApiKey(API_KEY) >> Optional.of(apiKey)
		1 * throttleRepositoryMock.findByApiKeyAndActiveTrue(API_KEY) >> Optional.of(throttle)
		1 * throttleRepositoryMock.decrementByApiKey(API_KEY, _)
		0 * _
		throttleStatistics.decremented
		throttleStatistics.total == API_KEY_HOURLY_LIMIT
		throttleStatistics.remaining == REMAINING_HITS_DECREMENTED
	}

	void "decrements existing IP when API key cannot be found, and returns statistics marked as successful"() {
		given:
		Throttle throttle = new Throttle(remainingHits: REMAINING_HITS)

		when:
		ThrottleStatistics throttleStatistics = throttleRepositoryImpl.decrementByApiKeyAndGetStatistics(API_KEY, IP_ADDRESS)

		then:
		1 * apiKeyRepositoryMock.findByApiKey(API_KEY) >> Optional.empty()
		1 * throttlePropertiesMock.ipAddressHourlyLimit >> IP_ADDRESS_HOURLY_LIMIT
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.of(throttle)
		1 * throttleRepositoryMock.decrementByIp(IP_ADDRESS, _)
		0 * _
		throttleStatistics.decremented
		throttleStatistics.total == IP_ADDRESS_HOURLY_LIMIT
		throttleStatistics.remaining == REMAINING_HITS_DECREMENTED
	}

	void "when decrementing by API key, returns statistics marked as not successful when there are 0 remaining hits"() {
		given:
		Throttle throttle = new Throttle(remainingHits: 0)
		ApiKey apiKey = new ApiKey(limit: API_KEY_HOURLY_LIMIT)

		when:
		ThrottleStatistics throttleStatistics = throttleRepositoryImpl.decrementByApiKeyAndGetStatistics(API_KEY, IP_ADDRESS)

		then:
		1 * apiKeyRepositoryMock.findByApiKey(API_KEY) >> Optional.of(apiKey)
		1 * throttleRepositoryMock.findByApiKeyAndActiveTrue(API_KEY) >> Optional.of(throttle)
		0 * _
		!throttleStatistics.decremented
		throttleStatistics.total == API_KEY_HOURLY_LIMIT
		throttleStatistics.remaining == 0
	}

	@SuppressWarnings('BracesForMethod')
	void """when decrementing by API key, returns statistics marked as not successful when there are less than 0 remaining hits,
			and sets remaining hits to 0"""() {
		given:
		Throttle throttle = new Throttle(remainingHits: -1)
		ApiKey apiKey = new ApiKey(limit: API_KEY_HOURLY_LIMIT)

		when:
		ThrottleStatistics throttleStatistics = throttleRepositoryImpl.decrementByApiKeyAndGetStatistics(API_KEY, IP_ADDRESS)

		then:
		1 * apiKeyRepositoryMock.findByApiKey(API_KEY) >> Optional.of(apiKey)
		1 * throttleRepositoryMock.findByApiKeyAndActiveTrue(API_KEY) >> Optional.of(throttle)
		0 * _
		!throttleStatistics.decremented
		throttleStatistics.total == API_KEY_HOURLY_LIMIT
		throttleStatistics.remaining == 0
	}

	void "regenerates IP addresses remaining hits limits"() {
		when:
		throttleRepositoryImpl.regenerateIPAddressesRemainingHits()

		then:
		1 * throttlePropertiesMock.ipAddressHourlyLimit >> IP_ADDRESS_HOURLY_LIMIT
		1 * throttleRepositoryMock.regenerateIPAddressesWithRemainingHits(IP_ADDRESS_HOURLY_LIMIT)
		0 * _
	}

	void "regenerates API keys remaining hits limit"() {
		given:
		QueryBuilder<ApiKey> apiKeyQueryBuilder = Mock()
		Throttle throttle1 = new Throttle(remainingHits: 5)
		Throttle throttle2 = new Throttle(remainingHits: 10)
		ApiKey apiKey1 = new ApiKey(throttle: throttle1, limit: API_KEY_1_LIMIT)
		ApiKey apiKey2 = new ApiKey(throttle: throttle2, limit: API_KEY_2_LIMIT)

		when:
		throttleRepositoryImpl.regenerateApiKeysRemainingHits()

		then:
		1 * apiKeyQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == 0
			assert pageable.pageSize == Integer.MAX_VALUE
			apiKeyQueryBuilder
		}
		1 * apiKeyQueryBuilder.fetch(ApiKey_.throttle)
		1 * apiKeyQueryBuilder.findAll() >> Lists.newArrayList(apiKey1, apiKey2)
		1 * throttleRepositoryMock.save(_ as List) >> { args ->
			List<Throttle> throttleList = (List<Throttle>) args[0]
			assert throttleList[0] == throttle1
			assert throttleList[0].remainingHits == API_KEY_1_LIMIT
			assert throttleList[1] == throttle2
			assert throttleList[1].remainingHits == API_KEY_2_LIMIT
		}
	}

	void "deletes expired IP limits"() {
		given:
		long beforeTestTime = toEpochSeconds(LocalDateTime.now())

		when:
		throttleRepositoryImpl.deleteExpiredIPLimits()

		then:
		1 * throttlePropertiesMock.minutesToDeleteExpiredIpAddresses >> MINUTES_TO_DELETE_EXPIRED_IP_ADDRESSES
		1 * throttleRepositoryMock.deleteIPAddressesOlderThan(_ as LocalDateTime) >> { LocalDateTime localDateTime ->
			long insideTestTime = toEpochSeconds(localDateTime.plusMinutes(MINUTES_TO_DELETE_EXPIRED_IP_ADDRESSES))
			long afterTestTime = toEpochSeconds(LocalDateTime.now())
			assert beforeTestTime <= insideTestTime
			assert afterTestTime >= insideTestTime
		}
		0 * _
	}

	private static long toEpochSeconds(LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault()
		localDateTime.atZone(zoneId).toEpochSecond()
	}

}
