package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class EpisodeTemplateEnrichingProcessorComposite implements ItemEnrichingProcessor<EnrichablePair<Page, EpisodeTemplate>> {

	private final EpisodeTemplateDatesEnrichingProcessor episodeTemplateDatesEnrichingProcessor;

	private final EpisodeTemplateTitleLanguagesEnrichingProcessor episodeTemplateTitleLanguagesEnrichingProcessor;

	public EpisodeTemplateEnrichingProcessorComposite(EpisodeTemplateDatesEnrichingProcessor episodeTemplateDatesEnrichingProcessor,
			EpisodeTemplateTitleLanguagesEnrichingProcessor episodeTemplateTitleLanguagesEnrichingProcessor) {
		this.episodeTemplateDatesEnrichingProcessor = episodeTemplateDatesEnrichingProcessor;
		this.episodeTemplateTitleLanguagesEnrichingProcessor = episodeTemplateTitleLanguagesEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, EpisodeTemplate> enrichablePair) throws Exception {
		episodeTemplateDatesEnrichingProcessor.enrich(enrichablePair);
		episodeTemplateTitleLanguagesEnrichingProcessor.enrich(enrichablePair);
	}
}
