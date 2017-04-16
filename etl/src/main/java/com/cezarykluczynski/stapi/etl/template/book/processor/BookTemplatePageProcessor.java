package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BookTemplatePageProcessor implements ItemProcessor<Page, BookTemplate> {

	@Override
	public BookTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
