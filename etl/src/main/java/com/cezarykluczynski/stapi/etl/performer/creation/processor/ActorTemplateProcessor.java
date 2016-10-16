package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ActorTemplateProcessor implements ItemProcessor<ActorTemplate, Performer> {

	@Override
	public Performer process(ActorTemplate item) throws Exception {
		Gender gender = item.getGender();

		return Performer.builder()
				.name(item.getName())
				.birthName(item.getBirthName())
				.gender(gender != null ? gender.name() : null)
				.build();
	}
}
