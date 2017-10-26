package com.cezarykluczynski.stapi.etl.book_series.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.book_series.processor.BookSeriesTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookSeriesProcessor extends CompositeItemProcessor<PageHeader, BookSeries> {

	public BookSeriesProcessor(PageHeaderProcessor pageHeaderProcessor, BookSeriesTemplatePageProcessor bookSeriesTemplatePageProcessor,
			BookSeriesTemplateProcessor bookSeriesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, bookSeriesTemplatePageProcessor,
				bookSeriesTemplateProcessor));
	}

}
