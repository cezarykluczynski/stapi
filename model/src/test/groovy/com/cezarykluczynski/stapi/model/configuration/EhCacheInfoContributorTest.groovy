package com.cezarykluczynski.stapi.model.configuration

import net.sf.ehcache.Cache
import net.sf.ehcache.CacheManager
import net.sf.ehcache.statistics.StatisticsGateway
import net.sf.ehcache.statistics.extended.ExtendedStatistics
import org.springframework.boot.actuate.info.Info
import org.springframework.boot.actuate.info.Info.Builder
import spock.lang.Specification

class EhCacheInfoContributorTest extends Specification {

	private static final String CACHE_NAME_1 = 'com.cezarykluczynski.stapi.model.magazine.entity'
	private static final String CACHE_NAME_2 = 'com.cezarykluczynski.stapi.model.book_collection.entity'
	private static final String CACHE_NAME_3 = 'com.cezarykluczynski.stapi.model.magazine.entity.Magazine.magazineSeries'
	private static final String CACHE_NAME_4 = 'com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection.bookSeries'
	private static final String CACHE_KEY_1 = 'com.cezarykluczynski.stapi.model.magazine'
	private static final String CACHE_KEY_2 = 'com.cezarykluczynski.stapi.model.book_collection'
	private static final Long STATISTIC_1 = 3
	private static final Long STATISTIC_2 = 7
	private static final Long STATISTIC_3 = 11
	private static final Long STATISTIC_4 = 17
	private static final Long MAGAZINE_STATISTICS_SUMMARY = 14
	private static final Long BOOK_COLLECTION_STATISTICS_SUMMARY = 24

	private CacheManager cacheManagerMock

	private EhCacheInfoContributor ehCacheInfoContributor

	void setup() {
		cacheManagerMock = Mock()
		ehCacheInfoContributor = new EhCacheInfoContributor(cacheManagerMock)
	}

	void "contributes all caches summarized"() {
		given:
		Builder builder = new Builder()
		String[] cacheNames = new String[4]
		cacheNames[0] = CACHE_NAME_1
		cacheNames[1] = CACHE_NAME_2
		cacheNames[2] = CACHE_NAME_3
		cacheNames[3] = CACHE_NAME_4
		Cache cache1 = Mock()
		Cache cache2 = Mock()
		Cache cache3 = Mock()
		Cache cache4 = Mock()
		StatisticsGateway statisticsGateway1 = Mock()
		StatisticsGateway statisticsGateway2 = Mock()
		StatisticsGateway statisticsGateway3 = Mock()
		StatisticsGateway statisticsGateway4 = Mock()
		ExtendedStatistics extendedStatistics1 = Mock()
		ExtendedStatistics extendedStatistics2 = Mock()
		ExtendedStatistics extendedStatistics3 = Mock()
		ExtendedStatistics extendedStatistics4 = Mock()
		ExtendedStatistics.Result result1 = Mock()
		ExtendedStatistics.Result result2 = Mock()
		ExtendedStatistics.Result result3 = Mock()
		ExtendedStatistics.Result result4 = Mock()
		ExtendedStatistics.Statistic statistic1 = Mock()
		ExtendedStatistics.Statistic statistic2 = Mock()
		ExtendedStatistics.Statistic statistic3 = Mock()
		ExtendedStatistics.Statistic statistic4 = Mock()

		when:
		ehCacheInfoContributor.contribute(builder)

		then:
		1 * cacheManagerMock.cacheNames >> cacheNames

		then:
		1 * cacheManagerMock.getCache(CACHE_NAME_1) >> cache1
		1 * cache1.statistics >> statisticsGateway1
		1 * statisticsGateway1.extended >> extendedStatistics1
		1 * extendedStatistics1.allPut() >> result1
		1 * result1.count() >> statistic1
		1 * statistic1.value() >> STATISTIC_1

		then:
		1 * cacheManagerMock.getCache(CACHE_NAME_2) >> cache2
		1 * cache2.statistics >> statisticsGateway2
		1 * statisticsGateway2.extended >> extendedStatistics2
		1 * extendedStatistics2.allPut() >> result2
		1 * result2.count() >> statistic2
		1 * statistic2.value() >> STATISTIC_2

		then:
		1 * cacheManagerMock.getCache(CACHE_NAME_3) >> cache3
		1 * cache3.statistics >> statisticsGateway3
		1 * statisticsGateway3.extended >> extendedStatistics3
		1 * extendedStatistics3.allPut() >> result3
		1 * result3.count() >> statistic3
		1 * statistic3.value() >> STATISTIC_3

		then:
		1 * cacheManagerMock.getCache(CACHE_NAME_4) >> cache4
		1 * cache4.statistics >> statisticsGateway4
		1 * statisticsGateway4.extended >> extendedStatistics4
		1 * extendedStatistics4.allPut() >> result4
		1 * result4.count() >> statistic4
		1 * statistic4.value() >> STATISTIC_4

		and:
		Info info = builder.build()
		info.details.get('ehcache') != null
		((Map) info.details.get('ehcache')).get(CACHE_KEY_1) == MAGAZINE_STATISTICS_SUMMARY
		((Map) info.details.get('ehcache')).get(CACHE_KEY_2) == BOOK_COLLECTION_STATISTICS_SUMMARY
	}

}
