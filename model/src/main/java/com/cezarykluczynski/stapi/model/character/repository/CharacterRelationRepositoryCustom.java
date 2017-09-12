package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;

import java.util.List;

public interface CharacterRelationRepositoryCustom {

	void linkAndSave(List<CharacterRelation> characterRelationList);

}
