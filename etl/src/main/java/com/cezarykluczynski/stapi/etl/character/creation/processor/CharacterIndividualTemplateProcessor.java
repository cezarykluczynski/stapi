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
		Character character = new Character();

		character.setName(item.getName());
		character.setPage(item.getPage());
		if (item.getGender() != null) {
			character.setGender(Gender.valueOf(item.getGender().name()));
		}
		character.setYearOfBirth(item.getYearOfBirth());
		character.setMonthOfBirth(item.getMonthOfBirth());
		character.setDayOfBirth(item.getDayOfBirth());
		character.setPlaceOfBirth(item.getPlaceOfBirth());
		character.setYearOfDeath(item.getYearOfDeath());
		character.setMonthOfDeath(item.getMonthOfDeath());
		character.setDayOfDeath(item.getDayOfDeath());
		character.setPlaceOfDeath(item.getPlaceOfDeath());
		character.setHeight(item.getHeight());
		character.setWeight(item.getWeight());
		character.setDeceased(item.getDeceased());
		character.setBloodType(item.getBloodType());
		character.setMaritalStatus(item.getMaritalStatus());
		character.setSerialNumber(item.getSerialNumber());
		character.getPerformers().addAll(item.getPerformers());
		item.getPerformers().forEach(performer -> performer.getCharacters().add(character));

		return character;
	}
}
