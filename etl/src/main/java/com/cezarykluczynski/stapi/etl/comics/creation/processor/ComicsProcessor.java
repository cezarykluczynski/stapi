package com.cezarykluczynski.stapi.etl.comics.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.processor.ComicsTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicsProcessor extends CompositeItemProcessor<PageHeader, Comics> {

	public ComicsProcessor(PageHeaderProcessor pageHeaderProcessor, ComicsTemplatePageProcessor comicSeriesTemplatePageProcessor,
			ComicsTemplateProcessor comicSeriesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, comicSeriesTemplatePageProcessor,
				comicSeriesTemplateProcessor));
	}

}
