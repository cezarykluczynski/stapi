package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class SpacecraftClassReader extends ListItemReader<PageHeader> {

	public SpacecraftClassReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of spacecraft classes list: {}", list.size());
	}

}
