package com.cezarykluczynski.stapi.etl.template.book.processor.collection;

import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionTemplatePageProcessor implements ItemProcessor<Page, BookCollectionTemplate> {

	@Override
	public BookCollectionTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
