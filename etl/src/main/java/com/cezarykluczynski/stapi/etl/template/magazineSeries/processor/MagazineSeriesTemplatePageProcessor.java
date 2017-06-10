package com.cezarykluczynski.stapi.etl.template.magazineSeries.processor;

import com.cezarykluczynski.stapi.etl.template.magazineSeries.dto.MagazineSeriesTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MagazineSeriesTemplatePageProcessor implements ItemProcessor<Page, MagazineSeriesTemplate> {

	public MagazineSeriesTemplatePageProcessor() {
	}

	@Override
	public MagazineSeriesTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
