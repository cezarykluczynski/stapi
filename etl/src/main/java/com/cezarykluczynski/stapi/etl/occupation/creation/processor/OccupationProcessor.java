package com.cezarykluczynski.stapi.etl.occupation.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class OccupationProcessor extends CompositeItemProcessor<PageHeader, Occupation> {

	public OccupationProcessor(PageHeaderProcessor pageHeaderProcessor, OccupationPageProcessor occupationPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, occupationPageProcessor));
	}

}

