package com.cezarykluczynski.stapi.etl.character.creation.processor;

import com.cezarykluczynski.stapi.etl.common.mapper.GenderMapper;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class CharacterTemplateProcessor implements ItemProcessor<CharacterTemplate, Character> {

	private final UidGenerator uidGenerator;

	private final GenderMapper genderMapper;

	public CharacterTemplateProcessor(UidGenerator uidGenerator, GenderMapper genderMapper) {
		this.uidGenerator = uidGenerator;
		this.genderMapper = genderMapper;
	}

	@Override
	public Character process(CharacterTemplate item) throws Exception {
		Character character = new Character();

		character.setName(item.getName());
		character.setPage(item.getPage());
		character.setUid(uidGenerator.generateFromPage(item.getPage(), Character.class));
		character.setGender(genderMapper.fromEtlToModel(item.getGender()));
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
		character.setHologramActivationDate(item.getHologramActivationDate());
		character.setHologramStatus(item.getHologramStatus());
		character.setHologramDateStatus(item.getHologramDateStatus());
		character.setHologram(Boolean.TRUE.equals(item.getHologram()));
		character.setFictionalCharacter(Boolean.TRUE.equals(item.getFictionalCharacter()));
		character.setMirror(Boolean.TRUE.equals(item.getMirror()));
		character.setAlternateReality(Boolean.TRUE.equals(item.getAlternateReality()));
		character.getPerformers().addAll(item.getPerformers());
		item.getPerformers().forEach(performer -> performer.getCharacters().add(character));
		character.getCharacterSpecies().addAll(item.getCharacterSpecies());
		character.getCharacterRelations().addAll(item.getCharacterRelations());
		character.getTitles().addAll(item.getTitles());
		character.getOccupations().addAll(item.getOccupations());
		character.getOrganizations().addAll(item.getOrganizations());

		return character;
	}

}
