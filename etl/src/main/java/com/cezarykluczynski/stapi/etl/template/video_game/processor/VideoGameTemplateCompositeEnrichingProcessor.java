package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class VideoGameTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, VideoGameTemplate>> {

	private final VideoGameTemplateContentsEnrichingProcessor videoTemplateContentsEnrichingProcessor;

	private final VideoGameTemplateRelationsEnrichingProcessor videoTemplateRelationsEnrichingProcessor;


	public VideoGameTemplateCompositeEnrichingProcessor(VideoGameTemplateContentsEnrichingProcessor videoTemplateContentsEnrichingProcessor,
			VideoGameTemplateRelationsEnrichingProcessor videoTemplateRelationsEnrichingProcessor) {
		this.videoTemplateContentsEnrichingProcessor = videoTemplateContentsEnrichingProcessor;
		this.videoTemplateRelationsEnrichingProcessor = videoTemplateRelationsEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, VideoGameTemplate> enrichablePair) throws Exception {
		videoTemplateContentsEnrichingProcessor.enrich(enrichablePair);
		videoTemplateRelationsEnrichingProcessor.enrich(enrichablePair);
	}

}
