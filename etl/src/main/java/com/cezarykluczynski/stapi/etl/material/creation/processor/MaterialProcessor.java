package com.cezarykluczynski.stapi.etl.material.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MaterialProcessor extends CompositeItemProcessor<PageHeader, Material> {

	public MaterialProcessor(PageHeaderProcessor pageHeaderProcessor, MaterialPageProcessor materialPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, materialPageProcessor));
	}

}
