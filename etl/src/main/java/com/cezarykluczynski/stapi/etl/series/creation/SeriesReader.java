package com.cezarykluczynski.stapi.etl.series.creation;

import com.cezarykluczynski.stapi.wiki.dto.PageHeader;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

public class SeriesReader extends ListItemReader<PageHeader> {

	SeriesReader(List<PageHeader> list) {
		super(list);
	}

}
