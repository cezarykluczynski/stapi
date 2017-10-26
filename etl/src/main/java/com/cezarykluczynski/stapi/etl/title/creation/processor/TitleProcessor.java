package com.cezarykluczynski.stapi.etl.title.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class TitleProcessor extends CompositeItemProcessor<PageHeader, Title> {

	public TitleProcessor(PageHeaderProcessor pageHeaderProcessor, TitlePageProcessor titlePageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, titlePageProcessor));
	}

}
