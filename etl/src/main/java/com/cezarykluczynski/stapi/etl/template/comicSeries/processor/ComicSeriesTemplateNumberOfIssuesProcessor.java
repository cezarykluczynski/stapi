package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.google.common.primitives.Ints;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

// TODO: remove?
@Service
public class ComicSeriesTemplateNumberOfIssuesProcessor implements ItemProcessor<String, Integer> {

	@Override
	public Integer process(String item) throws Exception {
		return Ints.tryParse(item);
	}

}
