package com.cezarykluczynski.stapi.etl.element.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class ElementReader extends ListItemReader<PageHeader> {

	public ElementReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of element list: {}", list.size());
	}

}
