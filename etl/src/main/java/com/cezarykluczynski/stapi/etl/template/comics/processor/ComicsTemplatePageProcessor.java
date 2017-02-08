package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicsTemplatePageProcessor implements ItemProcessor<Page, ComicsTemplate> {

	@Override
	public ComicsTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
