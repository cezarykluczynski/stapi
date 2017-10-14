package com.cezarykluczynski.stapi.etl.animal.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class AnimalReader extends ListItemReader<PageHeader> {

	public AnimalReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of animal list: {}", list.size());
	}

}
