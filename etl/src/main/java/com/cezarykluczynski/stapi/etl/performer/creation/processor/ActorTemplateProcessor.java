package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ActorTemplateProcessor implements ItemProcessor<ActorTemplate, Performer> {

	@Override
	public Performer process(ActorTemplate item) throws Exception {
		Gender gender = item.getGender();
		DateRange lifeRange = ObjectUtils.defaultIfNull(item.getLifeRange(), new DateRange());

		return Performer.builder()
				.name(item.getName())
				.page(item.getPage())
				.birthName(item.getBirthName())
				.dateOfBirth(lifeRange.getStartDate())
				.dateOfDeath(lifeRange.getEndDate())
				.gender(gender != null ? gender.name() : null)
				.build();
	}
}
