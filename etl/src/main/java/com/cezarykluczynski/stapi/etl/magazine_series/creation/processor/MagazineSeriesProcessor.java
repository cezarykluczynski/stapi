package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.magazine_series.processor.MagazineSeriesTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MagazineSeriesProcessor extends CompositeItemProcessor<PageHeader, MagazineSeries> {

	public MagazineSeriesProcessor(PageHeaderProcessor pageHeaderProcessor, MagazineSeriesTemplatePageProcessor magazineSeriesTemplatePageProcessor,
			MagazineSeriesTemplateProcessor magazineSeriesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, magazineSeriesTemplatePageProcessor, magazineSeriesTemplateProcessor));
	}

}
