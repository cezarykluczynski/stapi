package com.cezarykluczynski.stapi.etl.character.common.dto;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;

import java.util.HashMap;

@SuppressWarnings("IllegalType")
public class CharacterRelationsMap extends HashMap<CharacterRelationCacheKey, Template.Part> {

	public static CharacterRelationsMap of(CharacterRelationCacheKey characterRelationCacheKey, Template.Part templatePart) {
		CharacterRelationsMap characterRelationsMap = new CharacterRelationsMap();
		characterRelationsMap.put(characterRelationCacheKey, templatePart);
		return characterRelationsMap;
	}

}
