package com.cezarykluczynski.stapi.etl.book.creation.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class BookReader extends ListItemReader<PageHeader> {

	public BookReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of book list: {}", list.size());
	}

}
