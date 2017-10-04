package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.ContentRatingsProcessor;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
class VideoGameTemplateRelationsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<VideoGameTemplate> {

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final VideoGameTemplatePlatformsProcessor videoGameTemplatePlatformsProcessor;

	private final VideoGameTemplateGenresProcessor videoGameTemplateGenresProcessor;

	private final ContentRatingsProcessor contentRatingsProcessor;

	private final ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor;

	public VideoGameTemplateRelationsEnrichingProcessor(WikitextToEntitiesProcessor wikitextToEntitiesProcessor,
			VideoGameTemplatePlatformsProcessor videoGameTemplatePlatformsProcessor,
			VideoGameTemplateGenresProcessor videoGameTemplateGenresProcessor, ContentRatingsProcessor contentRatingsProcessor,
			ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor) {
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
		this.videoGameTemplatePlatformsProcessor = videoGameTemplatePlatformsProcessor;
		this.videoGameTemplateGenresProcessor = videoGameTemplateGenresProcessor;
		this.contentRatingsProcessor = contentRatingsProcessor;
		this.referencesFromTemplatePartProcessor = referencesFromTemplatePartProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, VideoGameTemplate> enrichablePair) throws Exception {
		Template sidebarVideoTemplate = enrichablePair.getInput();
		VideoGameTemplate videoGameTemplate = enrichablePair.getOutput();

		for (Template.Part part : sidebarVideoTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case VideoGameTemplateParameter.PUBLISHER:
					videoGameTemplate.getPublishers().addAll(wikitextToEntitiesProcessor.findCompanies(value));
					break;
				case VideoGameTemplateParameter.DEVELOPER:
					videoGameTemplate.getDevelopers().addAll(wikitextToEntitiesProcessor.findCompanies(value));
					break;
				case VideoGameTemplateParameter.PLATFORM:
					videoGameTemplate.getPlatforms().addAll(videoGameTemplatePlatformsProcessor.process(part));
					break;
				case VideoGameTemplateParameter.GENRES:
					videoGameTemplate.getGenres().addAll(videoGameTemplateGenresProcessor.process(value));
					break;
				case VideoGameTemplateParameter.RATING:
					videoGameTemplate.getRatings().addAll(contentRatingsProcessor.process(part));
					break;
				case VideoGameTemplateParameter.REFERENCE:
					videoGameTemplate.getReferences().addAll(referencesFromTemplatePartProcessor.process(part));
					break;
				default:
					break;
			}
		}
	}

}
