package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.template.common.processor.AbstractTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class IndividualTemplatePageProcessor extends AbstractTemplateProcessor
		implements ItemProcessor<Page, IndividualTemplate> {

	private static final String GENDER = "gender";

	private PartToGenderProcessor partToGenderProcessor;

	@Inject
	public IndividualTemplatePageProcessor(PartToGenderProcessor partToGenderProcessor) {
		this.partToGenderProcessor = partToGenderProcessor;
	}

	@Override
	public IndividualTemplate process(Page item) throws Exception {
		Optional<Template> templateOptional = findTemplate(item, TemplateName.SIDEBAR_INDIVIDUAL);

		if (!templateOptional.isPresent()) {
			return null;
		}

		Template template = templateOptional.get();
		IndividualTemplate individualTemplate = new IndividualTemplate();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch(key) {
				case GENDER:
					individualTemplate.setGender(partToGenderProcessor.process(part));
			}
		}

		return individualTemplate;
	}
}
