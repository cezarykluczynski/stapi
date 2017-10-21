package com.cezarykluczynski.stapi.etl.occupation.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class OccupationReader extends ListItemReader<PageHeader> {

	public OccupationReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of occupation list: {}", list.size());
	}

}
