package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SeasonProcessor extends CompositeItemProcessor<PageHeader, Season> {

	public SeasonProcessor(PageHeaderProcessor pageHeaderProcessor, SeasonPageProcessor literaturePageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, literaturePageProcessor));
	}

}
