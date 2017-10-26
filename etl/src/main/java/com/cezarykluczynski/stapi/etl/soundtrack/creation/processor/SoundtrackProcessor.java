package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.soundtrack.processor.SoundtrackTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackProcessor extends CompositeItemProcessor<PageHeader, Soundtrack> {

	public SoundtrackProcessor(PageHeaderProcessor pageHeaderProcessor, SoundtrackTemplatePageProcessor soundtrackTemplatePageProcessor,
			SoundtrackTemplateProcessor soundtrackTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, soundtrackTemplatePageProcessor, soundtrackTemplateProcessor));
	}

}
