package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CharacterRelationRepositoryImpl implements CharacterRelationRepositoryCustom {

	@Inject
	private CharacterRelationRepository characterRelationRepository;

	@Override
	public void linkAndSave(List<CharacterRelation> characterRelationList) {
		// TODO
	}

}
