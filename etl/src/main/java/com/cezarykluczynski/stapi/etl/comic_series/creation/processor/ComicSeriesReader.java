package com.cezarykluczynski.stapi.etl.comic_series.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class ComicSeriesReader extends ListItemReader<PageHeader> {

	public ComicSeriesReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of comic series list: {}", list.size());
	}

}
