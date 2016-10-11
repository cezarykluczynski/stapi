package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesWriterTest extends Specification {

	private SeriesRepository seriesRepositoryMock

	private SeriesWriter seriesWriter

	def setup() {
		seriesRepositoryMock = Mock(SeriesRepository)
		seriesWriter = new SeriesWriter(seriesRepositoryMock)
	}

	def "writes all entites using repository"() {
		given:
		List<Series> seriesList = Lists.newArrayList()

		when:
		seriesWriter.write(seriesList)

		then:
		1 * seriesRepositoryMock.save(seriesList)
	}

}