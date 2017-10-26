package com.cezarykluczynski.stapi.etl.book.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.book.processor.BookTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookProcessor extends CompositeItemProcessor<PageHeader, Book> {

	public BookProcessor(PageHeaderProcessor pageHeaderProcessor, BookTemplatePageProcessor comicSeriesTemplatePageProcessor,
			BookTemplateProcessor comicSeriesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, comicSeriesTemplatePageProcessor,
				comicSeriesTemplateProcessor));
	}

}
