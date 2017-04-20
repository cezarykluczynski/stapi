package com.cezarykluczynski.stapi.etl.bookSeries.creation.processor;

import com.cezarykluczynski.stapi.etl.template.bookSeries.dto.BookSeriesTemplate;
import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookSeriesTemplateProcessor implements ItemProcessor<BookSeriesTemplate, BookSeries> {

	private GuidGenerator guidGenerator;

	@Inject
	public BookSeriesTemplateProcessor(GuidGenerator guidGenerator) {
		this.guidGenerator = guidGenerator;
	}

	@Override
	public BookSeries process(BookSeriesTemplate item) throws Exception {
		BookSeries bookSeries = new BookSeries();

		bookSeries.setGuid(guidGenerator.generateFromPage(item.getPage(), BookSeries.class));
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
