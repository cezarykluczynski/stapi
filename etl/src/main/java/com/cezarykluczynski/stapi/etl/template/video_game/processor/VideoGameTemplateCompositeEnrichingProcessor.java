package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class VideoGameTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<VideoGameTemplate> {

	private final VideoGameTemplateContentsEnrichingProcessor videoGameTemplateContentsEnrichingProcessor;

	private final VideoGameTemplateRelationsEnrichingProcessor videoGameTemplateRelationsEnrichingProcessor;


	public VideoGameTemplateCompositeEnrichingProcessor(VideoGameTemplateContentsEnrichingProcessor videoGameTemplateContentsEnrichingProcessor,
			VideoGameTemplateRelationsEnrichingProcessor videoGameTemplateRelationsEnrichingProcessor) {
		this.videoGameTemplateContentsEnrichingProcessor = videoGameTemplateContentsEnrichingProcessor;
		this.videoGameTemplateRelationsEnrichingProcessor = videoGameTemplateRelationsEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, VideoGameTemplate> enrichablePair) throws Exception {
		videoGameTemplateContentsEnrichingProcessor.enrich(enrichablePair);
		videoGameTemplateRelationsEnrichingProcessor.enrich(enrichablePair);
	}

}
