package com.cezarykluczynski.stapi.server.series.query

import com.cezarykluczynski.stapi.client.soap.RequestPage
import com.cezarykluczynski.stapi.client.soap.SeriesRequest
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SeriesQueryBuilderTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_EMPTY = ''

	private SeriesRepository seriesRepositoryMock

	private PageMapper pageMapperMock

	private SeriesQueryBuilder seriesQueryBuilder

	def setup() {
		seriesRepositoryMock = Mock(SeriesRepository)
		pageMapperMock = Mock(PageMapper)
		seriesQueryBuilder = new SeriesQueryBuilder(seriesRepositoryMock, pageMapperMock)
	}

	def "when empty title is passed to rest method, all entities are returned"() {
		given:
		Page<Series> seriesPage = Mock(Page)
		PageRequest pageRequest = Mock(PageRequest)
		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams(title: TITLE_EMPTY)

		when:
		Page<Series> seriesPageResult = seriesQueryBuilder.query(seriesRestBeanParams)

		then:
		1 * pageMapperMock.fromPageAwareBeanParamsToPageRequest(seriesRestBeanParams) >> pageRequest
		1 * seriesRepositoryMock.findAll(pageRequest) >> seriesPage
		seriesPageResult == seriesPage

		then: 'no other interactions are expected'
		0 * _
	}

	def "when non empty title is passed to rest method, filter is used"() {
		given:
		Page<Series> seriesPage = Mock(Page)
		PageRequest pageRequest = Mock(PageRequest)
		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams(title: TITLE)

		when:
		Page<Series> seriesPageResult = seriesQueryBuilder.query(seriesRestBeanParams)

		then:
		1 * pageMapperMock.fromPageAwareBeanParamsToPageRequest(seriesRestBeanParams) >> pageRequest
		1 * seriesRepositoryMock.findByTitleIgnoreCaseContaining(TITLE, pageRequest) >> seriesPage
		seriesPageResult == seriesPage

		then: 'no other interactions are expected'
		0 * _
	}

	def "when empty title is passed to soap method, all entities are returned"() {
		given:
		List<Series> seriesList = Lists.newArrayList()
		Page<Series> seriesPage = Mock(Page) {
			getContent() >> seriesList
		}
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)

		when:
		Page<Series> seriesPageResult = seriesQueryBuilder.query(new SeriesRequest(
				title: TITLE_EMPTY,
				page: requestPage))

		then:
		1 * seriesRepositoryMock.findAll(pageRequest) >> seriesPage
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		seriesPageResult == seriesPage

		then: 'no other interactions are expected'
		0 * _
	}

	def "when non empty title is passed to soap method, filter is used"() {
		given:
		List<Series> seriesList = Lists.newArrayList()
		Page<Series> seriesPage = Mock(Page) {
			getContent() >> seriesList
		}
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)

		when:
		Page<Series> seriesPageResult = seriesQueryBuilder.query(new SeriesRequest(
				title: TITLE,
				page: requestPage))

		then:
		1 * seriesRepositoryMock.findByTitleIgnoreCaseContaining(TITLE, pageRequest) >> seriesPage
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		seriesPageResult == seriesPage

		then: 'no other interactions are expected'
		0 * _
	}

}
