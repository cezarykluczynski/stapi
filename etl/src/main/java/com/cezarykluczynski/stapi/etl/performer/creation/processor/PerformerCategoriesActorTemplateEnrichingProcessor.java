package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformerCategoriesActorTemplateEnrichingProcessor implements CategoriesActorTemplateEnrichingProcessor {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public PerformerCategoriesActorTemplateEnrichingProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<CategoryHeader>, ActorTemplate> enrichablePair) throws Exception {
		List<String> categoryTitlesList = categoryTitlesExtractingProcessor.process(enrichablePair.getInput());
		ActorTemplate actorTemplate = enrichablePair.getOutput();

		actorTemplate.setAnimalPerformer(categoryTitlesList.contains(CategoryTitle.ANIMAL_PERFORMERS));
		actorTemplate.setDisPerformer(categoryTitlesList.contains(CategoryTitle.DIS_PERFORMERS));
		actorTemplate.setDs9Performer(categoryTitlesList.contains(CategoryTitle.DS9_PERFORMERS));
		actorTemplate.setEntPerformer(categoryTitlesList.contains(CategoryTitle.ENT_PERFORMERS));
		actorTemplate.setFilmPerformer(categoryTitlesList.contains(CategoryTitle.FILM_PERFORMERS));
		actorTemplate.setStandInPerformer(categoryTitlesList.contains(CategoryTitle.STAND_INS));
		actorTemplate.setStuntPerformer(categoryTitlesList.contains(CategoryTitle.STUNT_PERFORMERS));
		actorTemplate.setTasPerformer(categoryTitlesList.contains(CategoryTitle.TAS_PERFORMERS));
		actorTemplate.setTngPerformer(categoryTitlesList.contains(CategoryTitle.TNG_PERFORMERS));
		actorTemplate.setTosPerformer(categoryTitlesList.contains(CategoryTitle.TOS_PERFORMERS)
				|| categoryTitlesList.contains(CategoryTitle.TOS_REMASTERED_PERFORMERS));
		actorTemplate.setVideoGamePerformer(categoryTitlesList.contains(CategoryTitle.VIDEO_GAME_PERFORMERS));
		actorTemplate.setVoicePerformer(categoryTitlesList.contains(CategoryTitle.VOICE_PERFORMERS));
		actorTemplate.setVoyPerformer(categoryTitlesList.contains(CategoryTitle.VOY_PERFORMERS));
	}

}
