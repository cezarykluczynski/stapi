package com.cezarykluczynski.stapi.etl.template.fictional.processor;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey;
import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.fictional.dto.FictionalTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class FictionalTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<CharacterTemplate> {

	private final PartToGenderProcessor partToGenderProcessor;

	private final CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor;

	private final CharactersRelationsCache charactersRelationsCache;

	private final CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor;

	public FictionalTemplateCompositeEnrichingProcessor(PartToGenderProcessor partToGenderProcessor,
			CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor, CharactersRelationsCache charactersRelationsCache,
			CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor) {
		this.partToGenderProcessor = partToGenderProcessor;
		this.characterSpeciesWikitextProcessor = characterSpeciesWikitextProcessor;
		this.charactersRelationsCache = charactersRelationsCache;
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
				case FictionalTemplateParameter.SPOUSE:
				case FictionalTemplateParameter.CHILDREN:
				case FictionalTemplateParameter.RELATIVE:
					charactersRelationsCache.put(characterTemplate.getPage().getPageId(), CharacterRelationsMap
							.of(CharacterRelationCacheKey.of(TemplateTitle.SIDEBAR_FICTIONAL, key), part));
					break;
				case FictionalTemplateParameter.ACTOR:
					characterTemplateActorLinkingEnrichingProcessor.enrich(EnrichablePair.of(value, characterTemplate));
					break;
				default:
					break;
			}
		}
	}

}
