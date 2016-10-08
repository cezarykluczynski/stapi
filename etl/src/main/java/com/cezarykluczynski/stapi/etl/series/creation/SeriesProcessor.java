package com.cezarykluczynski.stapi.etl.series.creation;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.wiki.dto.PageHeader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SeriesProcessor implements ItemProcessor<PageHeader, Series> {

	@Override
	public Series process(PageHeader item) throws Exception {
		// TODO: parse template
		return Series.builder()
				.title(item.getTitle())
				.build();
	}
}
