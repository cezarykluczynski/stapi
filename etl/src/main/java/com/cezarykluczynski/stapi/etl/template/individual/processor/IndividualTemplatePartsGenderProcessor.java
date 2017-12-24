package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndividualTemplatePartsGenderProcessor implements ItemProcessor<List<Template.Part>, Gender> {

	private final PartToGenderProcessor partToGenderProcessor;

	public IndividualTemplatePartsGenderProcessor(PartToGenderProcessor partToGenderProcessor) {
		this.partToGenderProcessor = partToGenderProcessor;
	}

	@Override
	public Gender process(List<Template.Part> templateParts) throws Exception {
		for (Template.Part part : templateParts) {
			String key = part.getKey();

			switch (key) {
				case IndividualTemplateParameter.GENDER:
					return partToGenderProcessor.process(part);
				default:
					break;
			}
		}

		return null;
	}

}
