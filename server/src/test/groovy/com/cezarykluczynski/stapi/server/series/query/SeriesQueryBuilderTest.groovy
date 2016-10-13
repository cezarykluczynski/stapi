package com.cezarykluczynski.stapi.server.series.query

import com.cezarykluczynski.stapi.client.soap.SeriesRequest
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesQueryBuilderTest extends Specification {

	private static final String TITLE = 'TITLE'

	private SeriesRepository seriesRepositoryMock

	private SeriesQueryBuilder seriesQueryBuilder

	def setup() {
		seriesRepositoryMock = Mock(SeriesRepository)
		seriesQueryBuilder = new SeriesQueryBuilder(seriesRepositoryMock)
	}

	def "when empty title is passed, all entities are returned"() {
		given:
		List<Series> seriesList = Lists.newArrayList()

		when:
		List<Series> seriesListResult = seriesQueryBuilder.query(new SeriesRequest(title: ""))

		then:
		1 * seriesRepositoryMock.findAll() >> seriesList
		seriesListResult == seriesList

		then: 'no other interactions are expected'
		0 * _
	}

	def "when non empty title is passed, filter is used"() {
		given:
		List<Series> seriesList = Lists.newArrayList()

		when:
		List<Series> seriesListResult = seriesQueryBuilder.query(new SeriesRequest(title: TITLE))

		then:
		1 * seriesRepositoryMock.findByTitleIgnoreCaseContaining(TITLE) >> seriesList
		seriesListResult == seriesList

		then: 'no other interactions are expected'
		0 * _
	}

}
