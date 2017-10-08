package com.cezarykluczynski.stapi.etl.conflict.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class ConflictReader extends ListItemReader<PageHeader> {

	public ConflictReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of conflicts list: {}", list.size());
	}

}
