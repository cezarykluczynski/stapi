package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class SeasonReader extends ListItemReader<PageHeader> {

	public SeasonReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of seasons list: {}", list.size());
	}

}
