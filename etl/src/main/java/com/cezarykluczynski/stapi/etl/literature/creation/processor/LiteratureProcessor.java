package com.cezarykluczynski.stapi.etl.literature.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class LiteratureProcessor extends CompositeItemProcessor<PageHeader, Literature> {

	public LiteratureProcessor(PageHeaderProcessor pageHeaderProcessor, LiteraturePageProcessor literaturePageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, literaturePageProcessor));
	}

}
