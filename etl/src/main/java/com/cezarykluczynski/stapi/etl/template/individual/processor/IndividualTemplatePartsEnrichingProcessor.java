package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey;
import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartListEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.MaritalStatusProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndividualTemplatePartsEnrichingProcessor implements ItemWithTemplatePartListEnrichingProcessor<CharacterTemplate> {

	private final PartToGenderProcessor partToGenderProcessor;

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor;

	private final CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor;

	private final IndividualHeightProcessor individualHeightProcessor;

	private final IndividualWeightProcessor individualWeightProcessor;

	private final IndividualSerialNumberProcessor individualSerialNumberProcessor;

	private final IndividualBloodTypeProcessor individualBloodTypeProcessor;

	private final CharactersRelationsCache charactersRelationsCache;

	private final MaritalStatusProcessor maritalStatusProcessor;

	private final CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor;

	@SuppressWarnings("ParameterNumber")
	public IndividualTemplatePartsEnrichingProcessor(PartToGenderProcessor partToGenderProcessor,
			WikitextToEntitiesProcessor wikitextToEntitiesProcessor, IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor,
			CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor,
			IndividualHeightProcessor individualHeightProcessor, IndividualWeightProcessor individualWeightProcessor,
			IndividualSerialNumberProcessor individualSerialNumberProcessor, IndividualBloodTypeProcessor individualBloodTypeProcessor,
			CharactersRelationsCache charactersRelationsCache, MaritalStatusProcessor maritalStatusProcessor,
			CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor) {
		this.partToGenderProcessor = partToGenderProcessor;
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
		this.individualLifeBoundaryProcessor = individualLifeBoundaryProcessor;
		this.characterTemplateActorLinkingEnrichingProcessor = characterTemplateActorLinkingEnrichingProcessor;
		this.individualHeightProcessor = individualHeightProcessor;
		this.individualWeightProcessor = individualWeightProcessor;
		this.individualSerialNumberProcessor = individualSerialNumberProcessor;
		this.individualBloodTypeProcessor = individualBloodTypeProcessor;
		this.charactersRelationsCache = charactersRelationsCache;
		this.maritalStatusProcessor = maritalStatusProcessor;
		this.characterSpeciesWikitextProcessor = characterSpeciesWikitextProcessor;
	}

	@Override
	@SuppressWarnings("CyclomaticComplexity")
	public void enrich(EnrichablePair<List<Template.Part>, CharacterTemplate> enrichablePair) throws Exception {
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case IndividualTemplateParameter.GENDER:
					characterTemplate.setGender(partToGenderProcessor.process(part));
					break;
				case IndividualTemplateParameter.AFFILIATION:
					characterTemplate.getOrganizations().addAll(wikitextToEntitiesProcessor.findOrganizations(value));
					break;
				case IndividualTemplateParameter.RANK:
					characterTemplate.getTitles().addAll(wikitextToEntitiesProcessor.findTitles(value));
					break;
				case IndividualTemplateParameter.OCCUPATION:
					characterTemplate.getOccupations().addAll(wikitextToEntitiesProcessor.findOccupations(value));
					break;
				case IndividualTemplateParameter.ACTOR:
					characterTemplateActorLinkingEnrichingProcessor.enrich(EnrichablePair.of(value, characterTemplate));
					break;
				case IndividualTemplateParameter.HEIGHT:
					characterTemplate.setHeight(individualHeightProcessor.process(value));
					break;
				case IndividualTemplateParameter.WEIGHT:
					characterTemplate.setWeight(individualWeightProcessor.process(value));
					break;
				case IndividualTemplateParameter.SERIAL_NUMBER:
					characterTemplate.setSerialNumber(individualSerialNumberProcessor.process(value));
					break;
				case IndividualTemplateParameter.BORN:
					IndividualLifeBoundaryDTO birthBoundaryDTO = individualLifeBoundaryProcessor.process(value);
					characterTemplate.setYearOfBirth(birthBoundaryDTO.getYear());
					characterTemplate.setMonthOfBirth(birthBoundaryDTO.getMonth());
					characterTemplate.setDayOfBirth(birthBoundaryDTO.getDay());
					break;
				case IndividualTemplateParameter.DIED:
					IndividualLifeBoundaryDTO deathBoundaryDTO = individualLifeBoundaryProcessor.process(value);
					characterTemplate.setYearOfDeath(deathBoundaryDTO.getYear());
					characterTemplate.setMonthOfDeath(deathBoundaryDTO.getMonth());
					characterTemplate.setDayOfDeath(deathBoundaryDTO.getDay());
					break;
				case IndividualTemplateParameter.FATHER:
				case IndividualTemplateParameter.MOTHER:
				case IndividualTemplateParameter.OWNER:
				case IndividualTemplateParameter.SIBLING:
				case IndividualTemplateParameter.RELATIVE:
				case IndividualTemplateParameter.SPOUSE:
				case IndividualTemplateParameter.CHILDREN:
					charactersRelationsCache.put(characterTemplate.getPage().getPageId(), CharacterRelationsMap
							.of(CharacterRelationCacheKey.of(TemplateTitle.SIDEBAR_INDIVIDUAL, key), part));
					break;
				case IndividualTemplateParameter.MARITAL_STATUS:
					characterTemplate.setMaritalStatus(maritalStatusProcessor.process(value));
					break;
				case IndividualTemplateParameter.BLOOD_TYPE:
					characterTemplate.setBloodType(individualBloodTypeProcessor.process(value));
					break;
				case IndividualTemplateParameter.SPECIES:
					characterTemplate.getCharacterSpecies().addAll(characterSpeciesWikitextProcessor.process(Pair.of(value, characterTemplate)));
					break;
				default:
					break;
			}
		}
	}

}
