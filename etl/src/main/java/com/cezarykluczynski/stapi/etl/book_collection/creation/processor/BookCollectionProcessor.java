package com.cezarykluczynski.stapi.etl.book_collection.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.book.processor.collection.BookCollectionTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionProcessor extends CompositeItemProcessor<PageHeader, BookCollection> {

	public BookCollectionProcessor(PageHeaderProcessor pageHeaderProcessor,
			BookCollectionTemplatePageProcessor bookCollectionTemplatePageProcessor,
			BookCollectionTemplateProcessor bookCollectionTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, bookCollectionTemplatePageProcessor,
				bookCollectionTemplateProcessor));
	}

}
