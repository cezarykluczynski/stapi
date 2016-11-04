package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.model.common.entity.RealWorldPerson;
import org.apache.commons.lang3.ObjectUtils;

public abstract class AbstractActorTemplateProcessor {

	protected void processCommonFields(RealWorldPerson realWorldPerson, ActorTemplate actorTemplate) {
		Gender gender = actorTemplate.getGender();
		DateRange lifeRange = ObjectUtils.defaultIfNull(actorTemplate.getLifeRange(), new DateRange());
		com.cezarykluczynski.stapi.model.common.entity.Gender genderModel = gender != null ?
				com.cezarykluczynski.stapi.model.common.entity.Gender.valueOf(gender.name()) : null;

		realWorldPerson.setName(actorTemplate.getName());
		realWorldPerson.setPage(actorTemplate.getPage());
		realWorldPerson.setBirthName(actorTemplate.getBirthName());
		realWorldPerson.setDateOfBirth(lifeRange.getStartDate());
		realWorldPerson.setPlaceOfBirth(actorTemplate.getPlaceOfBirth());
		realWorldPerson.setDateOfDeath(lifeRange.getEndDate());
		realWorldPerson.setPlaceOfDeath(actorTemplate.getPlaceOfDeath());
		realWorldPerson.setGender(genderModel);
	}

}
