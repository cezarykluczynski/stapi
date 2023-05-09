package com.cezarykluczynski.stapi.etl.book_series.creation.processor;

import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class BookSeriesWriter implements ItemWriter<BookSeries> {

	private final BookSeriesRepository bookSeriesRepository;

	public BookSeriesWriter(BookSeriesRepository bookSeriesRepository) {
		this.bookSeriesRepository = bookSeriesRepository;
	}

	@Override
	public void write(Chunk<? extends BookSeries> items) throws Exception {
		bookSeriesRepository.saveAll(items.getItems());
	}

}
