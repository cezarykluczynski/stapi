package com.cezarykluczynski.stapi.etl.element.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ElementProcessor extends CompositeItemProcessor<PageHeader, Element> {

	public ElementProcessor(PageHeaderProcessor pageHeaderProcessor, ElementPageProcessor elementPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, elementPageProcessor));
	}

}

