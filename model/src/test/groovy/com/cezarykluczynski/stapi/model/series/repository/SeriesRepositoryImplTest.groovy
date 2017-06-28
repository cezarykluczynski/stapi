package com.cezarykluczynski.stapi.model.series.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.entity.Series_
import com.cezarykluczynski.stapi.model.series.query.SeriesQueryBuilderFactory
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDate

class SeriesRepositoryImplTest extends Specification {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'
	private static final String ABBREVIATION = 'ABBREVIATION'
	private static final Integer PRODUCTION_START_YEAR_FROM = 1970
	private static final Integer PRODUCTION_START_YEAR_TO = 1995
	private static final Integer PRODUCTION_END_YEAR_FROM = 1980
	private static final Integer PRODUCTION_END_YEAR_TO = 2005
	private static final LocalDate ORIGINAL_RUN_START_FROM = LocalDate.of(1971, 1, 2)
	private static final LocalDate ORIGINAL_RUN_START_TO = LocalDate.of(1991, 3, 4)
	private static final LocalDate ORIGINAL_RUN_END_FROM = LocalDate.of(1998, 5, 6)
	private static final LocalDate ORIGINAL_RUN_END_TO = LocalDate.of(1999, 7, 8)
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private SeriesQueryBuilderFactory seriesQueryBuilderMock

	private SeriesRepositoryImpl seriesRepositoryImpl

	private QueryBuilder<Series> seriesQueryBuilder

	private Pageable pageable

	private SeriesRequestDTO seriesRequestDTO

	private Series series

	private Page page

	void setup() {
		seriesQueryBuilderMock = Mock()
		seriesRepositoryImpl = new SeriesRepositoryImpl(seriesQueryBuilderMock)
		seriesQueryBuilder = Mock()
		pageable = Mock()
		seriesRequestDTO = Mock()
		series = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		given:
		Episode episode = new Episode()

		when:
		Page pageOutput = seriesRepositoryImpl.findMatching(seriesRequestDTO, pageable)

		then:
		1 * seriesQueryBuilderMock.createQueryBuilder(pageable) >> seriesQueryBuilder

		then: 'uid criteria is set'
		1 * seriesRequestDTO.uid >> UID
		1 * seriesQueryBuilder.equal(Series_.uid, UID)

		then: 'string criteria are set'
		1 * seriesRequestDTO.title >> TITLE
		1 * seriesQueryBuilder.like(Series_.title, TITLE)
		1 * seriesRequestDTO.abbreviation >> ABBREVIATION
		1 * seriesQueryBuilder.like(Series_.abbreviation, ABBREVIATION)

		then: 'date criteria are set'
		1 * seriesRequestDTO.productionStartYearFrom >> PRODUCTION_START_YEAR_FROM
		1 * seriesRequestDTO.productionStartYearTo >> PRODUCTION_START_YEAR_TO
		1 * seriesQueryBuilder.between(Series_.productionStartYear, PRODUCTION_START_YEAR_FROM, PRODUCTION_START_YEAR_TO)
		1 * seriesRequestDTO.productionEndYearFrom >> PRODUCTION_END_YEAR_FROM
		1 * seriesRequestDTO.productionEndYearTo >> PRODUCTION_END_YEAR_TO
		1 * seriesQueryBuilder.between(Series_.productionEndYear, PRODUCTION_END_YEAR_FROM, PRODUCTION_END_YEAR_TO)
		1 * seriesRequestDTO.originalRunStartDateFrom >> ORIGINAL_RUN_START_FROM
		1 * seriesRequestDTO.originalRunStartDateTo >> ORIGINAL_RUN_START_TO
		1 * seriesQueryBuilder.between(Series_.originalRunStartDate, ORIGINAL_RUN_START_FROM, ORIGINAL_RUN_START_TO)
		1 * seriesRequestDTO.originalRunEndDateFrom >> ORIGINAL_RUN_END_FROM
		1 * seriesRequestDTO.originalRunEndDateTo >> ORIGINAL_RUN_END_TO
		1 * seriesQueryBuilder.between(Series_.originalRunEndDate, ORIGINAL_RUN_END_FROM, ORIGINAL_RUN_END_TO)

		then: 'sort is set'
		1 * seriesRequestDTO.sort >> SORT
		1 * seriesQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * seriesQueryBuilder.fetch(Series_.productionCompany)
		1 * seriesQueryBuilder.fetch(Series_.originalBroadcaster)

		then: 'fetch is performed with true flag'
		1 * seriesQueryBuilder.fetch(Series_.episodes, true)
		1 * seriesQueryBuilder.fetch(Series_.seasons, true)

		then: 'page is searched for'
		1 * seriesQueryBuilder.findPage() >> page

		then: 'series is set to episodes'
		1 * page.content >> Lists.newArrayList(series)
		1 * series.episodes >> Lists.newArrayList(episode)
		episode.series == series

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = seriesRepositoryImpl.findMatching(seriesRequestDTO, pageable)

		then:
		1 * seriesQueryBuilderMock.createQueryBuilder(pageable) >> seriesQueryBuilder

		then: 'uid criteria is set to null'
		1 * seriesRequestDTO.uid >> null

		then: 'fetch is performed'
		1 * seriesQueryBuilder.fetch(Series_.productionCompany)
		1 * seriesQueryBuilder.fetch(Series_.originalBroadcaster)

		then: 'fetch is performed with false flag'
		1 * seriesQueryBuilder.fetch(Series_.episodes, false)
		1 * seriesQueryBuilder.fetch(Series_.seasons, false)

		then: 'page is searched for and returned'
		1 * seriesQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(series)
		1 * series.setEpisodes(Sets.newHashSet())
		1 * series.setSeasons(Sets.newHashSet())
		pageOutput == page
	}

}
