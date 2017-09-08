package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.MaritalStatusProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class IndividualTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, CharacterTemplate>> {

	private final PartToGenderProcessor partToGenderProcessor;

	private final IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor;

	private final IndividualTemplateActorLinkingProcessor individualTemplateActorLinkingProcessor;

	private final IndividualHeightProcessor individualHeightProcessor;

	private final IndividualWeightProcessor individualWeightProcessor;

	private final IndividualBloodTypeProcessor individualBloodTypeProcessor;

	private final MaritalStatusProcessor maritalStatusProcessor;

	private final CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor;

	@Inject
	@SuppressWarnings("ParameterNumber")
	public IndividualTemplatePartsEnrichingProcessor(PartToGenderProcessor partToGenderProcessor,
			IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor,
			IndividualTemplateActorLinkingProcessor individualTemplateActorLinkingProcessor, IndividualHeightProcessor individualHeightProcessor,
			IndividualWeightProcessor individualWeightProcessor, IndividualBloodTypeProcessor individualBloodTypeProcessor,
			MaritalStatusProcessor maritalStatusProcessor, CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor) {
		this.partToGenderProcessor = partToGenderProcessor;
		this.individualLifeBoundaryProcessor = individualLifeBoundaryProcessor;
		this.individualTemplateActorLinkingProcessor = individualTemplateActorLinkingProcessor;
		this.individualHeightProcessor = individualHeightProcessor;
		this.individualWeightProcessor = individualWeightProcessor;
		this.individualBloodTypeProcessor = individualBloodTypeProcessor;
		this.maritalStatusProcessor = maritalStatusProcessor;
		this.characterSpeciesWikitextProcessor = characterSpeciesWikitextProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, CharacterTemplate> enrichablePair) throws Exception {
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case IndividualTemplateParameter.GENDER:
					characterTemplate.setGender(partToGenderProcessor.process(part));
					break;
				case IndividualTemplateParameter.ACTOR:
					individualTemplateActorLinkingProcessor.enrich(EnrichablePair.of(part, characterTemplate));
					break;
				case IndividualTemplateParameter.HEIGHT:
					characterTemplate.setHeight(individualHeightProcessor.process(value));
					break;
				case IndividualTemplateParameter.WEIGHT:
					characterTemplate.setWeight(individualWeightProcessor.process(value));
					break;
				case IndividualTemplateParameter.SERIAL_NUMBER:
					if (StringUtils.isNotBlank(value)) {
						characterTemplate.setSerialNumber(value);
					}
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
