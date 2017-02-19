package com.cezarykluczynski.stapi.etl.comicStrip.creation.processor;

import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicStripProcessor implements ItemProcessor<PageHeader, ComicStrip> {

	@Override
	public ComicStrip process(PageHeader item) throws Exception {
		// TODO
		return null;
	}

}
