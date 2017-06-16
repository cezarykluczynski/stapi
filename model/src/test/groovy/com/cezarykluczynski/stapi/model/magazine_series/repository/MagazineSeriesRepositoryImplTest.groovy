package com.cezarykluczynski.stapi.model.magazine_series.repository

import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries_
import com.cezarykluczynski.stapi.model.magazine_series.query.MagazineSeriesQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractMagazineSeriesTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class MagazineSeriesRepositoryImplTest extends AbstractMagazineSeriesTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private MagazineSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory

	private MagazineSeriesRepositoryImpl comicSeriesRepositoryImpl

	private QueryBuilder<MagazineSeries> comicSeriesQueryBuilder

	private Pageable pageable

	private MagazineSeriesRequestDTO comicSeriesRequestDTO

	private MagazineSeries comicSeries

	private Page page

	void setup() {
		comicSeriesQueryBuilderFactory = Mock()
		comicSeriesRepositoryImpl = new MagazineSeriesRepositoryImpl(comicSeriesQueryBuilderFactory)
		comicSeriesQueryBuilder = Mock()
		pageable = Mock()
		comicSeriesRequestDTO = Mock()
		page = Mock()
		comicSeries = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = comicSeriesRepositoryImpl.findMatching(comicSeriesRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicSeriesQueryBuilderFactory.createQueryBuilder(pageable) >> comicSeriesQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * comicSeriesRequestDTO.uid >> UID
		1 * comicSeriesQueryBuilder.equal(MagazineSeries_.uid, UID)

		then: 'string criteria are set'
		1 * comicSeriesRequestDTO.title >> TITLE
		1 * comicSeriesQueryBuilder.like(MagazineSeries_.title, TITLE)

		then: 'integer criteria are set'
		1 * comicSeriesRequestDTO.publishedYearFrom >> PUBLISHED_YEAR_FROM
		1 * comicSeriesQueryBuilder.between(MagazineSeries_.publishedYearFrom, PUBLISHED_YEAR_FROM, null)
		1 * comicSeriesRequestDTO.publishedYearTo >> PUBLISHED_YEAR_TO
		1 * comicSeriesQueryBuilder.between(MagazineSeries_.publishedYearTo, null, PUBLISHED_YEAR_TO)

		1 * comicSeriesRequestDTO.numberOfIssuesFrom >> NUMBER_OF_ISSUES_FROM
		1 * comicSeriesRequestDTO.numberOfIssuesTo >> NUMBER_OF_ISSUES_TO
		1 * comicSeriesQueryBuilder.between(MagazineSeries_.numberOfIssues, NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO)

		then: 'sort is set'
		1 * comicSeriesRequestDTO.sort >> SORT
		1 * comicSeriesQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * comicSeriesQueryBuilder.fetch(MagazineSeries_.publishers, true)
		1 * comicSeriesQueryBuilder.fetch(MagazineSeries_.editors, true)
		1 * comicSeriesQueryBuilder.fetch(MagazineSeries_.magazines, true)

		then: 'page is retrieved'
		1 * comicSeriesQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = comicSeriesRepositoryImpl.findMatching(comicSeriesRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicSeriesQueryBuilderFactory.createQueryBuilder(pageable) >> comicSeriesQueryBuilder

		then: 'uid criteria is set to null'
		1 * comicSeriesRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * comicSeriesQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(comicSeries)
		1 * comicSeries.setPublishers(Sets.newHashSet())
		1 * comicSeries.setEditors(Sets.newHashSet())
		1 * comicSeries.setMagazines(Sets.newHashSet())
		pageOutput == page
	}

}
