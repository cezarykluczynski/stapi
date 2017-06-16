package com.cezarykluczynski.stapi.model.comic_series.repository

import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries_
import com.cezarykluczynski.stapi.model.comic_series.query.ComicSeriesQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractComicSeriesTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class ComicSeriesRepositoryImplTest extends AbstractComicSeriesTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private ComicSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory

	private ComicSeriesRepositoryImpl comicSeriesRepositoryImpl

	private QueryBuilder<ComicSeries> comicSeriesQueryBuilder

	private Pageable pageable

	private ComicSeriesRequestDTO comicSeriesRequestDTO

	private ComicSeries comicSeries

	private Page page

	void setup() {
		comicSeriesQueryBuilderFactory = Mock()
		comicSeriesRepositoryImpl = new ComicSeriesRepositoryImpl(comicSeriesQueryBuilderFactory)
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
		1 * comicSeriesQueryBuilder.equal(ComicSeries_.uid, UID)

		then: 'string criteria are set'
		1 * comicSeriesRequestDTO.title >> TITLE
		1 * comicSeriesQueryBuilder.like(ComicSeries_.title, TITLE)

		then: 'integer criteria are set'
		1 * comicSeriesRequestDTO.publishedYearFrom >> PUBLISHED_YEAR_FROM
		1 * comicSeriesQueryBuilder.between(ComicSeries_.publishedYearFrom, PUBLISHED_YEAR_FROM, null)
		1 * comicSeriesRequestDTO.publishedYearTo >> PUBLISHED_YEAR_TO
		1 * comicSeriesQueryBuilder.between(ComicSeries_.publishedYearTo, null, PUBLISHED_YEAR_TO)

		1 * comicSeriesRequestDTO.numberOfIssuesFrom >> NUMBER_OF_ISSUES_FROM
		1 * comicSeriesRequestDTO.numberOfIssuesTo >> NUMBER_OF_ISSUES_TO
		1 * comicSeriesQueryBuilder.between(ComicSeries_.numberOfIssues, NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO)

		1 * comicSeriesRequestDTO.yearFrom >> YEAR_FROM
		1 * comicSeriesQueryBuilder.between(ComicSeries_.yearFrom, YEAR_FROM, null)
		1 * comicSeriesRequestDTO.yearTo >> YEAR_TO
		1 * comicSeriesQueryBuilder.between(ComicSeries_.yearTo, null, YEAR_TO)

		then: 'float criteria are set'
		1 * comicSeriesRequestDTO.stardateFrom >> STARDATE_FROM
		1 * comicSeriesQueryBuilder.between(ComicSeries_.stardateFrom, STARDATE_FROM, null)
		1 * comicSeriesRequestDTO.stardateTo >> STARDATE_TO
		1 * comicSeriesQueryBuilder.between(ComicSeries_.stardateTo, null, STARDATE_TO)

		then: 'boolean criteria are set'
		1 * comicSeriesRequestDTO.miniseries >> MINISERIES
		1 * comicSeriesQueryBuilder.equal(ComicSeries_.miniseries, MINISERIES)
		1 * comicSeriesRequestDTO.photonovelSeries >> PHOTONOVEL_SERIES
		1 * comicSeriesQueryBuilder.equal(ComicSeries_.photonovelSeries, PHOTONOVEL_SERIES)

		then: 'sort is set'
		1 * comicSeriesRequestDTO.sort >> SORT
		1 * comicSeriesQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * comicSeriesQueryBuilder.fetch(ComicSeries_.parentSeries, true)
		1 * comicSeriesQueryBuilder.fetch(ComicSeries_.childSeries, true)
		1 * comicSeriesQueryBuilder.fetch(ComicSeries_.publishers, true)
		1 * comicSeriesQueryBuilder.fetch(ComicSeries_.comics, true)

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
		1 * comicSeries.setParentSeries(Sets.newHashSet())
		1 * comicSeries.setChildSeries(Sets.newHashSet())
		1 * comicSeries.setPublishers(Sets.newHashSet())
		1 * comicSeries.setComics(Sets.newHashSet())
		pageOutput == page
	}

}
