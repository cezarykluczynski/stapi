package com.cezarykluczynski.stapi.etl.template.comicStrip.processor;

import com.cezarykluczynski.stapi.etl.template.comicStrip.dto.ComicStripTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicStripTemplatePageProcessor implements ItemProcessor<Page, ComicStripTemplate> {

	@Override
	public ComicStripTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
