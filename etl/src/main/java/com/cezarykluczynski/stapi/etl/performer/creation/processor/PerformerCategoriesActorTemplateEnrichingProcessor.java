package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.AbstractCategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformerCategoriesActorTemplateEnrichingProcessor
		extends AbstractCategoriesActorTemplateEnrichingProcessor implements CategoriesActorTemplateEnrichingProcessor {

	@Override
	public void enrich(EnrichablePair<List<CategoryHeader>, ActorTemplate> enrichablePair) {
		List<String> categoryTitlesList = getCategoryTitlesList(enrichablePair);
		ActorTemplate actorTemplate = enrichablePair.getOutput();

		actorTemplate.setAnimalPerformer(categoryTitlesList.contains(CategoryName.ANIMAL_PERFORMERS));
		actorTemplate.setDisPerformer(categoryTitlesList.contains(CategoryName.DIS_PERFORMERS));
		actorTemplate.setDs9Performer(categoryTitlesList.contains(CategoryName.DS9_PERFORMERS));
		actorTemplate.setEntPerformer(categoryTitlesList.contains(CategoryName.ENT_PERFORMERS));
		actorTemplate.setFilmPerformer(categoryTitlesList.contains(CategoryName.FILM_PERFORMERS));
		actorTemplate.setStandInPerformer(categoryTitlesList.contains(CategoryName.STAND_INS));
		actorTemplate.setStuntPerformer(categoryTitlesList.contains(CategoryName.STUNT_PERFORMERS));
		actorTemplate.setTasPerformer(categoryTitlesList.contains(CategoryName.TAS_PERFORMERS));
		actorTemplate.setTngPerformer(categoryTitlesList.contains(CategoryName.TNG_PERFORMERS));
		actorTemplate.setTosPerformer(categoryTitlesList.contains(CategoryName.TOS_PERFORMERS)
				|| categoryTitlesList.contains(CategoryName.TOS_REMASTERED_PERFORMERS));
		actorTemplate.setVideoGamePerformer(categoryTitlesList.contains(CategoryName.VIDEO_GAME_PERFORMERS));
		actorTemplate.setVoicePerformer(categoryTitlesList.contains(CategoryName.VOICE_PERFORMERS));
		actorTemplate.setVoyPerformer(categoryTitlesList.contains(CategoryName.VOY_PERFORMERS));
	}

}
