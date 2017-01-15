package com.cezarykluczynski.stapi.etl.template.characterbox.processor;

import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate;
import com.cezarykluczynski.stapi.etl.template.common.processor.MaritalStatusProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualHeightProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualWeightProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterboxTemplateProcessor implements ItemProcessor<Page, CharacterboxTemplate> {

	private static final String GENDER = "gender";
	private static final String HEIGHT = "height";
	private static final String WEIGHT = "weight";
	private static final String MARITAL_STATUS = "marital status";

	private TemplateFinder templateFinder;

	private PartToGenderProcessor partToGenderProcessor;

	private IndividualHeightProcessor individualHeightProcessor;

	private IndividualWeightProcessor individualWeightProcessor;

	private MaritalStatusProcessor maritalStatusProcessor;

	public CharacterboxTemplateProcessor(TemplateFinder templateFinder, PartToGenderProcessor partToGenderProcessor,
			IndividualHeightProcessor individualHeightProcessor, IndividualWeightProcessor individualWeightProcessor,
			MaritalStatusProcessor maritalStatusProcessor) {
		this.templateFinder = templateFinder;
		this.partToGenderProcessor = partToGenderProcessor;
		this.individualHeightProcessor = individualHeightProcessor;
		this.individualWeightProcessor = individualWeightProcessor;
		this.maritalStatusProcessor = maritalStatusProcessor;
	}

	@Override
	public CharacterboxTemplate process(Page item) throws Exception {
		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateName.CHARACTER_BOX);

		if (!templateOptional.isPresent()) {
			return null;
		}

		Template template = templateOptional.get();
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case GENDER:
					characterboxTemplate.setGender(partToGenderProcessor.process(part));
					break;
				case HEIGHT:
					characterboxTemplate.setHeight(individualHeightProcessor.process(value));
					break;
				case WEIGHT:
					characterboxTemplate.setWeight(individualWeightProcessor.process(value));
					break;
				case MARITAL_STATUS:
					characterboxTemplate.setMaritalStatus(maritalStatusProcessor.process(value));
					break;
				default:
					break;
			}
		}

		return characterboxTemplate;
	}
}
