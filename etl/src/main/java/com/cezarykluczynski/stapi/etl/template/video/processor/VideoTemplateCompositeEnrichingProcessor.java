package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class VideoTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<VideoTemplate> {

	private final VideoTemplateContentsEnrichingProcessor videoTemplateContentsEnrichingProcessor;

	private final VideoTemplateDatesEnrichingProcessor videoTemplateDatesEnrichingProcessor;

	private final VideoTemplateDigitalFormatsEnrichingProcessor videoTemplateDigitalFormatsEnrichingProcessor;

	private final VideoTemplateRelationsEnrichingProcessor videoTemplateRelationsEnrichingProcessor;

	public VideoTemplateCompositeEnrichingProcessor(VideoTemplateContentsEnrichingProcessor videoTemplateContentsEnrichingProcessor,
			VideoTemplateDatesEnrichingProcessor videoTemplateDatesEnrichingProcessor,
			VideoTemplateDigitalFormatsEnrichingProcessor videoTemplateDigitalFormatsEnrichingProcessor,
			VideoTemplateRelationsEnrichingProcessor videoTemplateRelationsEnrichingProcessor) {
		this.videoTemplateContentsEnrichingProcessor = videoTemplateContentsEnrichingProcessor;
		this.videoTemplateDatesEnrichingProcessor = videoTemplateDatesEnrichingProcessor;
		this.videoTemplateDigitalFormatsEnrichingProcessor = videoTemplateDigitalFormatsEnrichingProcessor;
		this.videoTemplateRelationsEnrichingProcessor = videoTemplateRelationsEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, VideoTemplate> enrichablePair) throws Exception {
		videoTemplateContentsEnrichingProcessor.enrich(enrichablePair);
		videoTemplateDatesEnrichingProcessor.enrich(enrichablePair);
		videoTemplateDigitalFormatsEnrichingProcessor.enrich(enrichablePair);
		videoTemplateRelationsEnrichingProcessor.enrich(enrichablePair);
	}

}
