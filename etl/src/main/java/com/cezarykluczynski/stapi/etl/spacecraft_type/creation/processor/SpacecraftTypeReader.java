package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class SpacecraftTypeReader extends ListItemReader<PageHeader> {

	public SpacecraftTypeReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of spacecraft types list: {}", list.size());
	}

}
