package com.cezarykluczynski.stapi.etl.character.common.service;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CharactersRelationsCache {

	private final Map<Long, CharacterRelationsMap> cache = Maps.newHashMap();

	public void put(Long pageId, CharacterRelationsMap characterRelationsMap) {
		cache.putIfAbsent(pageId, new CharacterRelationsMap());
		cache.get(pageId).putAll(characterRelationsMap);
	}

	public CharacterRelationsMap get(Long pageId) {
		CharacterRelationsMap cachedCharacterRelationsMap = cache.get(pageId);

		if (cachedCharacterRelationsMap == null) {
			return null;
		}

		CharacterRelationsMap newCharacterRelationsMap = new CharacterRelationsMap();
		newCharacterRelationsMap.putAll(cachedCharacterRelationsMap);
		return newCharacterRelationsMap;
	}

	public boolean isEmpty() {
		return cache.isEmpty();
	}

}
