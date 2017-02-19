package com.cezarykluczynski.stapi.etl.comicStrip.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.comicStrip.processor.ComicStripTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicStripProcessor extends CompositeItemProcessor<PageHeader, ComicStrip> {

	@Inject
	public ComicStripProcessor(PageHeaderProcessor pageHeaderProcessor, ComicStripTemplatePageProcessor comicStripTemplatePageProcessor,
			ComicStripTemplateProcessor comicStripTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, comicStripTemplatePageProcessor,
				comicStripTemplateProcessor));
	}

}
