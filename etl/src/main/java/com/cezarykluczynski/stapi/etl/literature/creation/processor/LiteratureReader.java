package com.cezarykluczynski.stapi.etl.literature.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class LiteratureReader extends ListItemReader<PageHeader> {

	public LiteratureReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of literature list: {}", list.size());
	}

}
