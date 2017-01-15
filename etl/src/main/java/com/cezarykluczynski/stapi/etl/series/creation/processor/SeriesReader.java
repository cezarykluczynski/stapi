package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class SeriesReader extends ListItemReader<PageHeader> {

	public SeriesReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of series list: {}", list.size());
	}

}
