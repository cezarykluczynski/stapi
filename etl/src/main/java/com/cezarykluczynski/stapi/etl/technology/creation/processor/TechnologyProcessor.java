package com.cezarykluczynski.stapi.etl.technology.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class TechnologyProcessor extends CompositeItemProcessor<PageHeader, Technology> {

	public TechnologyProcessor(PageHeaderProcessor pageHeaderProcessor, TechnologyPageProcessor technologyPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, technologyPageProcessor));
	}

}

