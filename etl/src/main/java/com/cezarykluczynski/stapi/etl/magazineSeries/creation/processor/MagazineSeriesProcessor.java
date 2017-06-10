package com.cezarykluczynski.stapi.etl.magazineSeries.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.magazineSeries.processor.MagazineSeriesTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.magazineSeries.entity.MagazineSeries;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MagazineSeriesProcessor extends CompositeItemProcessor<PageHeader, MagazineSeries> {

	@Inject
	public MagazineSeriesProcessor(PageHeaderProcessor pageHeaderProcessor, MagazineSeriesTemplatePageProcessor magazineSeriesTemplatePageProcessor,
			MagazineSeriesTemplateProcessor magazineSeriesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, magazineSeriesTemplatePageProcessor, magazineSeriesTemplateProcessor));
	}

}
