package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.wiki.dto.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ActorTemplateTemplateProcessor implements ItemProcessor<Template, ActorTemplate> {

	private static final String NAME = "name";
	private static final String BIRTH_NAME = "birth name";
	private static final String GENDER = "gender";

	private PartToGenderProcessor partToGenderProcessor;

	@Inject
	public ActorTemplateTemplateProcessor(PartToGenderProcessor partToGenderProcessor) {
		this.partToGenderProcessor = partToGenderProcessor;
	}

	@Override
	public ActorTemplate process(Template item) throws Exception {
		ActorTemplate actorTemplate = new ActorTemplate();

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();
			String value = part.getValue();
			switch(key) {
				case NAME:
					if (StringUtils.isNotBlank(value)) {
						actorTemplate.setName(value);
					}
					break;
				case BIRTH_NAME:
					actorTemplate.setBirthName(value);
					break;
				case GENDER:
					actorTemplate.setGender(partToGenderProcessor.process(part));
					break;
			}
		}

		return actorTemplate;
	}

}
