package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.CharacterApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.CharacterFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.CharacterSearchCriteria;

public class Character {

	private final CharacterApi characterApi;


	public Character(CharacterApi characterApi) {
		this.characterApi = characterApi;
	}

	public CharacterFullResponse get(String uid) throws ApiException {
		return characterApi.v1GetCharacter(uid);
	}

	public CharacterBaseResponse search(CharacterSearchCriteria characterSearchCriteria) throws ApiException {
		return characterApi.v1SearchCharacters(characterSearchCriteria.getPageNumber(), characterSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(characterSearchCriteria.getSort()), characterSearchCriteria.getName(),
				characterSearchCriteria.getGender(), characterSearchCriteria.getDeceased(), characterSearchCriteria.getHologram(),
				characterSearchCriteria.getFictionalCharacter(), characterSearchCriteria.getMirror(), characterSearchCriteria.getAlternateReality());
	}

}
