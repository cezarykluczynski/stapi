package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class MagazineSeriesReader extends ListItemReader<PageHeader> {

	public MagazineSeriesReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of magazine series list: {}", list.size());
	}

}
