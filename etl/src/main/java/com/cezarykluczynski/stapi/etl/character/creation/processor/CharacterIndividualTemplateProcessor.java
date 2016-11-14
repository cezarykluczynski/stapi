package com.cezarykluczynski.stapi.etl.character.creation.processor;

import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.entity.Gender;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class CharacterIndividualTemplateProcessor implements ItemProcessor<IndividualTemplate, Character> {

	@Override
	public Character process(IndividualTemplate item) throws Exception {
		// TODO
		Character character = new Character();
		if (item.getGender() != null) {
			character.setGender(Gender.valueOf(item.getGender().name()));
		}

		return character;
	}
}
