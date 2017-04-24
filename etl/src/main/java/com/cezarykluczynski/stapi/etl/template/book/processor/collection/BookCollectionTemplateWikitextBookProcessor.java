package com.cezarykluczynski.stapi.etl.template.book.processor.collection;

import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookCollectionTemplateWikitextBookProcessor implements ItemProcessor<Page, Set<Book>> {

	@Override
	public Set<Book> process(Page item) throws Exception {
		// TODO
		return null;
	}

}
