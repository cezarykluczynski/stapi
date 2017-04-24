package com.cezarykluczynski.stapi.etl.bookCollection.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.book.processor.collection.BookCollectionTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookCollectionProcessor extends CompositeItemProcessor<PageHeader, BookCollection> {

	@Inject
	public BookCollectionProcessor(PageHeaderProcessor pageHeaderProcessor,
			BookCollectionTemplatePageProcessor bookCollectionTemplatePageProcessor,
			BookCollectionTemplateProcessor bookCollectionTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, bookCollectionTemplatePageProcessor,
				bookCollectionTemplateProcessor));
	}

}
