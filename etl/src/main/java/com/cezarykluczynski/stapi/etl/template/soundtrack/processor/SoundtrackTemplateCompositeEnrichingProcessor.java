package com.cezarykluczynski.stapi.etl.template.soundtrack.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate;
import org.springframework.stereotype.Service;


@Service
public class SoundtrackTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<SoundtrackTemplate> {

	private final SoundtrackTemplateContentsEnrichingProcessor soundtrackTemplateContentsEnrichingProcessor;

	private final SoundtrackTemplateRelationsEnrichingProcessor soundtrackTemplateRelationsEnrichingProcessor;


	public SoundtrackTemplateCompositeEnrichingProcessor(SoundtrackTemplateContentsEnrichingProcessor soundtrackTemplateContentsEnrichingProcessor,
			SoundtrackTemplateRelationsEnrichingProcessor soundtrackTemplateRelationsEnrichingProcessor) {
		this.soundtrackTemplateContentsEnrichingProcessor = soundtrackTemplateContentsEnrichingProcessor;
		this.soundtrackTemplateRelationsEnrichingProcessor = soundtrackTemplateRelationsEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, SoundtrackTemplate> enrichablePair) throws Exception {
		soundtrackTemplateContentsEnrichingProcessor.enrich(enrichablePair);
		soundtrackTemplateRelationsEnrichingProcessor.enrich(enrichablePair);
	}

}
