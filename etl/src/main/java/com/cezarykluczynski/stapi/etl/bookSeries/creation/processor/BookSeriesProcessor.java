package com.cezarykluczynski.stapi.etl.bookSeries.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.bookSeries.processor.BookSeriesTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookSeriesProcessor extends CompositeItemProcessor<PageHeader, BookSeries> {

	@Inject
	public BookSeriesProcessor(PageHeaderProcessor pageHeaderProcessor, BookSeriesTemplatePageProcessor bookSeriesTemplatePageProcessor,
			BookSeriesTemplateProcessor bookSeriesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, bookSeriesTemplatePageProcessor,
				bookSeriesTemplateProcessor));
	}

}
