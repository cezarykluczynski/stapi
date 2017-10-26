package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
public class CharacterRelationRepositoryImpl implements CharacterRelationRepositoryCustom {

	private final CharacterRepository characterRepository;

	@Inject
	private CharacterRelationRepository characterRelationRepository;

	public CharacterRelationRepositoryImpl(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	@Override
	public void linkAndSave(List<CharacterRelation> characterRelationList) {
		Set<Character> characterSet = Sets.newHashSet();

		for (CharacterRelation characterRelation : characterRelationList) {
			CharacterRelation persistedCharacterRelation = characterRelationRepository.save(characterRelation);
			persistedCharacterRelation.getSource().getCharacterRelations().add(persistedCharacterRelation);
			persistedCharacterRelation.getTarget().getCharacterRelations().add(persistedCharacterRelation);
			characterSet.add(persistedCharacterRelation.getSource());
			characterSet.add(persistedCharacterRelation.getTarget());
		}

		characterRepository.save(characterSet);
	}

}
