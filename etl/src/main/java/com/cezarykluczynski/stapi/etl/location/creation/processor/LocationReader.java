package com.cezarykluczynski.stapi.etl.location.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class LocationReader extends ListItemReader<PageHeader> {

	public LocationReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of location list: {}", list.size());
	}

}
