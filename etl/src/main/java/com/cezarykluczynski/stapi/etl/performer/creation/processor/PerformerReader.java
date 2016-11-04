package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class PerformerReader extends ListItemReader<PageHeader> {

	public PerformerReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of performers list: {}", list.size());
	}

}
