package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ActorTemplateTemplateProcessor implements ItemProcessor<Template, ActorTemplate> {

	private PartToGenderProcessor partToGenderProcessor;

	public ActorTemplateTemplateProcessor(PartToGenderProcessor partToGenderProcessor) {
		this.partToGenderProcessor = partToGenderProcessor;
	}

	@Override
	@NonNull
	public ActorTemplate process(Template item) throws Exception {
		ActorTemplate actorTemplate = new ActorTemplate();

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (StringUtils.isBlank(value)) {
				continue;
			}

			switch (key) {
				case ActorTemplateParameter.NAME:
					actorTemplate.setName(value);
					break;
				case ActorTemplateParameter.BIRTH_NAME:
					actorTemplate.setBirthName(value);
					break;
				case ActorTemplateParameter.BIRTHPLACE:
					actorTemplate.setPlaceOfBirth(value);
					break;
				case ActorTemplateParameter.DEATHPLACE:
					actorTemplate.setPlaceOfDeath(value);
					break;
				case ActorTemplateParameter.GENDER:
					actorTemplate.setGender(partToGenderProcessor.process(part));
					break;
				default:
					break;
			}
		}

		return actorTemplate;
	}

}
