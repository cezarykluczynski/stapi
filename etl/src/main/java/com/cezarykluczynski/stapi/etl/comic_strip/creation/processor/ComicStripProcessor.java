package com.cezarykluczynski.stapi.etl.comic_strip.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.comic_strip.processor.ComicStripTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicStripProcessor extends CompositeItemProcessor<PageHeader, ComicStrip> {

	public ComicStripProcessor(PageHeaderProcessor pageHeaderProcessor, ComicStripTemplatePageProcessor comicStripTemplatePageProcessor,
			ComicStripTemplateProcessor comicStripTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, comicStripTemplatePageProcessor,
				comicStripTemplateProcessor));
	}

}
