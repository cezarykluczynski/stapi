package com.cezarykluczynski.stapi.etl.book_collection.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class BookCollectionReader extends ListItemReader<PageHeader> {

	public BookCollectionReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of book collection list: {}", list.size());
	}

}
