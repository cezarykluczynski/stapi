package com.cezarykluczynski.stapi.etl.book_series.creation.processor;

import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookSeriesTemplateProcessor implements ItemProcessor<BookSeriesTemplate, BookSeries> {

	private final UidGenerator uidGenerator;

	public BookSeriesTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public BookSeries process(BookSeriesTemplate item) throws Exception {
		BookSeries bookSeries = new BookSeries();

		bookSeries.setUid(uidGenerator.generateFromPage(item.getPage(), BookSeries.class));
		bookSeries.setPage(item.getPage());
		bookSeries.setTitle(item.getTitle());
		bookSeries.setPublishedYearFrom(item.getPublishedYearFrom());
		bookSeries.setPublishedMonthFrom(item.getPublishedMonthFrom());
		bookSeries.setPublishedYearTo(item.getPublishedYearTo());
		bookSeries.setPublishedMonthTo(item.getPublishedMonthTo());
		bookSeries.setNumberOfBooks(item.getNumberOfBooks());
		bookSeries.setYearFrom(item.getYearFrom());
		bookSeries.setYearTo(item.getYearTo());
		bookSeries.setMiniseries(Boolean.TRUE.equals(item.getMiniseries()));
		bookSeries.setEBookSeries(Boolean.TRUE.equals(item.getEBookSeries()));
		bookSeries.getPublishers().addAll(item.getPublishers());

		return bookSeries;

	}

}
