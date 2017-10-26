package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftTypeProcessor extends CompositeItemProcessor<PageHeader, SpacecraftType> {

	public SpacecraftTypeProcessor(PageHeaderProcessor pageHeaderProcessor, SpacecraftTypePageProcessor spacecraftTypePageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, spacecraftTypePageProcessor));
	}

}
