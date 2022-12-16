package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.CharacterApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFullResponse;

@SuppressWarnings("ParameterNumber")
public class Character {

	private final CharacterApi characterApi;


	public Character(CharacterApi characterApi) {
		this.characterApi = characterApi;
	}

	public CharacterFullResponse get(String uid) throws ApiException {
		return characterApi.v1RestCharacterGet(uid, null);
	}

	public CharacterBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String gender, Boolean deceased,
			Boolean hologram, Boolean fictionalCharacter, Boolean mirror, Boolean alternateReality) throws ApiException {
		return characterApi.v1RestCharacterSearchPost(pageNumber, pageSize, sort, null, name, gender, deceased, hologram, fictionalCharacter,
				mirror, alternateReality);
	}

}
