package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class SpacecraftReader extends ListItemReader<PageHeader> {

	public SpacecraftReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of spacecrafts list: {}", list.size());
	}

}
