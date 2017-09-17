package com.cezarykluczynski.stapi.etl.title.creation.processor;

import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class TitlePageProcessor implements ItemProcessor<Page, Title> {

	@Override
	public Title process(Page item) throws Exception {
		// TODO
		return null;
	}

}
