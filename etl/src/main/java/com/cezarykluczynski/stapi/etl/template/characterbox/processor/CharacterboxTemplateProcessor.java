package com.cezarykluczynski.stapi.etl.template.characterbox.processor;

import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate;
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.processor.MaritalStatusProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualHeightProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualLifeBoundaryProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualWeightProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterboxTemplateProcessor implements ItemProcessor<Page, CharacterboxTemplate> {

	private final TemplateFinder templateFinder;

	private final PartToGenderProcessor partToGenderProcessor;

	private final IndividualHeightProcessor individualHeightProcessor;

	private final IndividualWeightProcessor individualWeightProcessor;

	private final IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor;

	private final MaritalStatusProcessor maritalStatusProcessor;

	public CharacterboxTemplateProcessor(TemplateFinder templateFinder, PartToGenderProcessor partToGenderProcessor,
			IndividualHeightProcessor individualHeightProcessor, IndividualWeightProcessor individualWeightProcessor,
			MaritalStatusProcessor maritalStatusProcessor, IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor) {
		this.templateFinder = templateFinder;
		this.partToGenderProcessor = partToGenderProcessor;
		this.individualHeightProcessor = individualHeightProcessor;
		this.individualWeightProcessor = individualWeightProcessor;
		this.maritalStatusProcessor = maritalStatusProcessor;
		this.individualLifeBoundaryProcessor = individualLifeBoundaryProcessor;
	}

	@Override
	public CharacterboxTemplate process(Page item) throws Exception {
		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.CHARACTER_BOX);

		if (!templateOptional.isPresent()) {
			return null;
		}

		Template template = templateOptional.get();
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case CharacterboxTemplateParameter.GENDER:
					characterboxTemplate.setGender(partToGenderProcessor.process(part));
					break;
				case CharacterboxTemplateParameter.HEIGHT:
					characterboxTemplate.setHeight(individualHeightProcessor.process(value));
					break;
				case CharacterboxTemplateParameter.WEIGHT:
					characterboxTemplate.setWeight(individualWeightProcessor.process(value));
					break;
				case CharacterboxTemplateParameter.BORN:
					IndividualLifeBoundaryDTO birthBoundaryDTO = individualLifeBoundaryProcessor.process(value);
					characterboxTemplate.setYearOfBirth(birthBoundaryDTO.getYear());
					characterboxTemplate.setMonthOfBirth(birthBoundaryDTO.getMonth());
					characterboxTemplate.setDayOfBirth(birthBoundaryDTO.getDay());
					break;
				case CharacterboxTemplateParameter.DIED:
					IndividualLifeBoundaryDTO deathBoundaryDTO = individualLifeBoundaryProcessor.process(value);
					characterboxTemplate.setYearOfDeath(deathBoundaryDTO.getYear());
					characterboxTemplate.setMonthOfDeath(deathBoundaryDTO.getMonth());
					characterboxTemplate.setDayOfDeath(deathBoundaryDTO.getDay());
					break;
				case CharacterboxTemplateParameter.MARITAL_STATUS:
					characterboxTemplate.setMaritalStatus(maritalStatusProcessor.process(value));
					break;
				default:
					break;
			}
		}

		return characterboxTemplate;
	}

}
