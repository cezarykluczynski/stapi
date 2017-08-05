package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor;
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.ContentRatingsProcessor;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
class VideoGameTemplateRelationsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, VideoGameTemplate>> {

	private final WikitextToCompaniesProcessor wikitextToCompaniesProcessor;

	private final VideoGameTemplateGenresProcessor videoGameTemplateGenresProcessor;

	private final ContentRatingsProcessor contentRatingsProcessor;

	private final ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor;

	public VideoGameTemplateRelationsEnrichingProcessor(WikitextToCompaniesProcessor wikitextToCompaniesProcessor,
			VideoGameTemplateGenresProcessor videoGameTemplateGenresProcessor, ContentRatingsProcessor contentRatingsProcessor,
			ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor) {
		this.wikitextToCompaniesProcessor = wikitextToCompaniesProcessor;
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
					videoGameTemplate.getPublishers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case VideoGameTemplateParameter.DEVELOPER:
					videoGameTemplate.getDevelopers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case VideoGameTemplateParameter.PLATFORM:
					// TODO
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
