package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class EpisodeTemplateDatesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Page, EpisodeTemplate>> {

	@Override
	public void enrich(EnrichablePair<Page, EpisodeTemplate> enrichablePair) {
		// TODO
	}

}
