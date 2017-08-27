package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class StarshipTemplatePageProcessor implements ItemProcessor<Page, StarshipTemplate> {

	@Override
	public StarshipTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
