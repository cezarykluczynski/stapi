package com.cezarykluczynski.stapi.etl.technology.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class TechnologyReader extends ListItemReader<PageHeader> {

	public TechnologyReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of technology list: {}", list.size());
	}

}
