package com.cezarykluczynski.stapi.etl.character.link.relation.service;

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CharacterRelationsInversionService {

	private static final Map<String, String> MAPPINGS = Maps.newHashMap();

	static {
		MAPPINGS.put(CharacterRelationName.FATHER, CharacterRelationName.CHILD);
		MAPPINGS.put(CharacterRelationName.MOTHER, CharacterRelationName.CHILD);
		MAPPINGS.put(CharacterRelationName.OWNER, CharacterRelationName.PROPERTY);
		MAPPINGS.put(CharacterRelationName.SIBLING, CharacterRelationName.SIBLING);
		MAPPINGS.put(CharacterRelationName.RELATIVE, CharacterRelationName.RELATIVE);
		MAPPINGS.put(CharacterRelationName.SPOUSE, CharacterRelationName.SPOUSE);
		MAPPINGS.put(CharacterRelationName.CHILD, CharacterRelationName.PARENT);
		MAPPINGS.put(CharacterRelationName.CREATOR, CharacterRelationName.CREATION);
		MAPPINGS.put(CharacterRelationName.ORIGINAL_CHARACTER, CharacterRelationName.HOLOGRAM);
		MAPPINGS.put(CharacterRelationName.PROPERTY, CharacterRelationName.OWNER);
		MAPPINGS.put(CharacterRelationName.PARENT, CharacterRelationName.CHILD);
		MAPPINGS.put(CharacterRelationName.CREATION, CharacterRelationName.CREATOR);
		MAPPINGS.put(CharacterRelationName.HOLOGRAM, CharacterRelationName.ORIGINAL_CHARACTER);
	}

	public String invert(String characterRelationName) {
		return MAPPINGS.get(characterRelationName);
	}

}
