package com.cezarykluczynski.stapi.etl.template.fictional.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.fictional.dto.FictionalTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class FictionalTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<CharacterTemplate> {

	private final PartToGenderProcessor partToGenderProcessor;

	private final CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor;

	private final CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor;

	@Inject
	public FictionalTemplateCompositeEnrichingProcessor(PartToGenderProcessor partToGenderProcessor,
			CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor,
			CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor) {
		this.partToGenderProcessor = partToGenderProcessor;
		this.characterSpeciesWikitextProcessor = characterSpeciesWikitextProcessor;
		this.characterTemplateActorLinkingEnrichingProcessor = characterTemplateActorLinkingEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, CharacterTemplate> enrichablePair) throws Exception {
		Template sidebarHologramTemplate = enrichablePair.getInput();
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		characterTemplate.setFictionalCharacter(true);

		for (Template.Part part : sidebarHologramTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case FictionalTemplateParameter.GENDER:
					characterTemplate.setGender(partToGenderProcessor.process(part));
					break;
				case FictionalTemplateParameter.SPECIES:
					characterTemplate.getCharacterSpecies().addAll(characterSpeciesWikitextProcessor.process(Pair.of(value, characterTemplate)));
					break;
				case FictionalTemplateParameter.CREATOR:
				case FictionalTemplateParameter.CHARACTER:
					// TODO
					break;
				case FictionalTemplateParameter.ACTOR:
					characterTemplateActorLinkingEnrichingProcessor.enrich(EnrichablePair.of(part, characterTemplate));
					break;
				default:
					break;
			}
		}
	}

}
