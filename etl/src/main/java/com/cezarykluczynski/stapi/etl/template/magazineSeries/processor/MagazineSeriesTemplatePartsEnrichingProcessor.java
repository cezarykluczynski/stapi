package com.cezarykluczynski.stapi.etl.template.magazineSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.magazineSeries.dto.MagazineSeriesTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagazineSeriesTemplatePartsEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, MagazineSeriesTemplate>> {

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, MagazineSeriesTemplate> enrichablePair) throws Exception {
		// TODO
	}

}
