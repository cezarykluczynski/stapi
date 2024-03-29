package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class SeriesWriterTest extends Specification {

	private SeriesRepository seriesRepositoryMock

	private SeriesWriter seriesWriter

	void setup() {
		seriesRepositoryMock = Mock()
		seriesWriter = new SeriesWriter(seriesRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Series series = new Series()
		List<Series> seriesList = Lists.newArrayList(series)

		when:
		seriesWriter.write(new Chunk(seriesList))

		then:
		1 * seriesRepositoryMock.saveAll(seriesList)
		0 * _
	}

}
