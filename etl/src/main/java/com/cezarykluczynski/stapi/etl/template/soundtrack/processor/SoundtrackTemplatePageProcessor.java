package com.cezarykluczynski.stapi.etl.template.soundtrack.processor;

import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackTemplatePageProcessor implements ItemProcessor<Page, SoundtrackTemplate> {

	@Override
	public SoundtrackTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
