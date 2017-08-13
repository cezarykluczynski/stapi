package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class StarshipClassTemplatePageProcessor implements ItemProcessor<Page, StarshipClassTemplate> {

	@Override
	public StarshipClassTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
