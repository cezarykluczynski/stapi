package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.series.processor.SeriesTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.wiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesProcessor extends CompositeItemProcessor<PageHeader, Series> {

	private PageHeaderProcessor pageHeaderProcessor;

	private SeriesTemplatePageProcessor seriesTemplatePageProcessor;

	private SeriesTemplateProcessor seriesTemplateProcessor;

	@Inject
	public SeriesProcessor(PageHeaderProcessor pageHeaderProcessor, SeriesTemplatePageProcessor seriesTemplatePageProcessor,
			SeriesTemplateProcessor seriesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, seriesTemplatePageProcessor, seriesTemplateProcessor));
	}

}
