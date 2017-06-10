package com.cezarykluczynski.stapi.etl.template.magazine.processor;

import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MagazineTemplatePageProcessor implements ItemProcessor<Page, MagazineTemplate> {

	public MagazineTemplatePageProcessor() {
	}

	@Override
	public MagazineTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
