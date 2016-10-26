package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

public class SeriesReader extends ListItemReader<PageHeader> {

	SeriesReader(List<PageHeader> list) {
		super(list);
	}

}
