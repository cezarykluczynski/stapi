package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesBookTemplateEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<CategoryHeader>, BookTemplate>> {

	@Override
	public void enrich(EnrichablePair<List<CategoryHeader>, BookTemplate> enrichablePair) throws Exception {
		// TODO
	}

}
