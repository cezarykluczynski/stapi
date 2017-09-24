package com.cezarykluczynski.stapi.etl.material.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class MaterialReader extends ListItemReader<PageHeader> {

	public MaterialReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of material list: {}", list.size());
	}

}
