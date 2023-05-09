package com.cezarykluczynski.stapi.etl.book_series.creation.processor

import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class BookSeriesWriterTest extends Specification {

	private BookSeriesRepository bookSeriesRepositoryMock

	private BookSeriesWriter bookSeriesWriterMock

	void setup() {
		bookSeriesRepositoryMock = Mock()
		bookSeriesWriterMock = new BookSeriesWriter(bookSeriesRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		BookSeries bookSeries = new BookSeries()
		List<BookSeries> bookSeriesList = Lists.newArrayList(bookSeries)

		when:
		bookSeriesWriterMock.write(new Chunk(bookSeriesList))

		then:
		1 * bookSeriesRepositoryMock.saveAll(bookSeriesList)
		0 * _
	}

}
