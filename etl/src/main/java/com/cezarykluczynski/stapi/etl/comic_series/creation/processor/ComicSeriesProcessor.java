package com.cezarykluczynski.stapi.etl.comic_series.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.comic_series.processor.ComicSeriesTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesProcessor extends CompositeItemProcessor<PageHeader, ComicSeries> {

	public ComicSeriesProcessor(PageHeaderProcessor pageHeaderProcessor, ComicSeriesTemplatePageProcessor comicSeriesTemplatePageProcessor,
			ComicSeriesTemplateProcessor comicSeriesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, comicSeriesTemplatePageProcessor,
				comicSeriesTemplateProcessor));
	}

}
