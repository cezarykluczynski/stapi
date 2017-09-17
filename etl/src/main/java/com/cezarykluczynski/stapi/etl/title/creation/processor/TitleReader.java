package com.cezarykluczynski.stapi.etl.title.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class TitleReader extends ListItemReader<PageHeader> {

	public TitleReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of title list: {}", list.size());
	}

}
