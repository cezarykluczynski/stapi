package com.cezarykluczynski.stapi.etl.comicSeries.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.comicSeries.processor.ComicSeriesTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicSeriesProcessor extends CompositeItemProcessor<PageHeader, ComicSeries> {

	@Inject
	public ComicSeriesProcessor(PageHeaderProcessor pageHeaderProcessor, ComicSeriesTemplatePageProcessor comicSeriesTemplatePageProcessor,
			ComicSeriesTemplateProcessor comicSeriesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, comicSeriesTemplatePageProcessor,
				comicSeriesTemplateProcessor));
	}

}
