package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.ContentLanguagesProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.ContentRatingsProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class VideoTemplateRelationsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<VideoTemplate> {

	private final ContentRatingsProcessor contentRatingsProcessor;

	private final ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor;

	private final ContentLanguagesProcessor contentLanguagesProcessor;

	public VideoTemplateRelationsEnrichingProcessor(ContentRatingsProcessor contentRatingsProcessor,
			ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor, ContentLanguagesProcessor contentLanguagesProcessor) {
		this.contentRatingsProcessor = contentRatingsProcessor;
		this.referencesFromTemplatePartProcessor = referencesFromTemplatePartProcessor;
		this.contentLanguagesProcessor = contentLanguagesProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, VideoTemplate> enrichablePair) throws Exception {
		Template sidebarVideoTemplate = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();

		for (Template.Part part : sidebarVideoTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case VideoTemplateParameter.RATING:
					videoTemplate.getRatings().addAll(contentRatingsProcessor.process(part));
					break;
				case VideoTemplateParameter.LANGUAGE:
					videoTemplate.getLanguages().addAll(contentLanguagesProcessor.process(value));
					break;
				case VideoTemplateParameter.SUBTITLES:
					videoTemplate.getLanguagesSubtitles().addAll(contentLanguagesProcessor.process(value));
					break;
				case VideoTemplateParameter.DUBBED:
					videoTemplate.getLanguagesDubbed().addAll(contentLanguagesProcessor.process(value));
					break;
				case VideoTemplateParameter.REFERENCE:
					videoTemplate.getReferences().addAll(referencesFromTemplatePartProcessor.process(part));
					break;
				default:
					break;
			}
		}
	}

}
