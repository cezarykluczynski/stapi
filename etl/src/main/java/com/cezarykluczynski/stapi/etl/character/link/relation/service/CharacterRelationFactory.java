package com.cezarykluczynski.stapi.etl.character.link.relation.service;

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterPageLinkWithRelationName;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import org.springframework.stereotype.Service;

@Service
public class CharacterRelationFactory {

	private final CharacterRelationsSidebarTemplateMappingsProvider characterRelationsSidebarTemplateMappingsProvider;

	public CharacterRelationFactory(CharacterRelationsSidebarTemplateMappingsProvider characterRelationsSidebarTemplateMappingsProvider) {
		this.characterRelationsSidebarTemplateMappingsProvider = characterRelationsSidebarTemplateMappingsProvider;
	}

	public CharacterRelation create(Character character, CharacterPageLinkWithRelationName characterPageLinkWithRelationName) {
		// TODO
		return null;
	}

}
