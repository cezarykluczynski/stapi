package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class BookTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, BookTemplate>> {

	@Override
	public void enrich(EnrichablePair<Template, BookTemplate> enrichablePair) throws Exception {
		// TODO
	}

}
