package com.cezarykluczynski.stapi.etl.template.bookSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.bookSeries.dto.BookSeriesTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookSeriesTemplateFixedValuesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<BookSeriesTemplate, BookSeriesTemplate>> {

	@Override
	public void enrich(EnrichablePair<BookSeriesTemplate, BookSeriesTemplate> enrichablePair) throws Exception {
		// TODO
	}

}
