package com.cezarykluczynski.stapi.etl.species.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class SpeciesReader extends ListItemReader<PageHeader> {

	public SpeciesReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of species list: {}", list.size());
	}

}
