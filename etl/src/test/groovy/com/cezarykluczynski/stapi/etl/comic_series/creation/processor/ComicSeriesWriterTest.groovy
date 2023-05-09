package com.cezarykluczynski.stapi.etl.comic_series.creation.processor

import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class ComicSeriesWriterTest extends Specification {

	private ComicSeriesRepository comicSeriesRepositoryMock

	private ComicSeriesWriter comicSeriesWriterMock

	void setup() {
		comicSeriesRepositoryMock = Mock()
		comicSeriesWriterMock = new ComicSeriesWriter(comicSeriesRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		ComicSeries comicSeries = new ComicSeries()
		List<ComicSeries> comicSeriesList = Lists.newArrayList(comicSeries)

		when:
		comicSeriesWriterMock.write(new Chunk(comicSeriesList))

		then:
		1 * comicSeriesRepositoryMock.saveAll(comicSeriesList)
		0 * _
	}

}
