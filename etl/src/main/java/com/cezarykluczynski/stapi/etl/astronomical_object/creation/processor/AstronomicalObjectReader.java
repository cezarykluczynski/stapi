package com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class AstronomicalObjectReader extends ListItemReader<PageHeader> {

	public AstronomicalObjectReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of astronomical object list: {}", list.size());
	}

}
