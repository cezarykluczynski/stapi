package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.CharacterApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFullResponse;

@SuppressWarnings("ParameterNumber")
public class Character {

	private final CharacterApi characterApi;

	private final String apiKey;

	public Character(CharacterApi characterApi, String apiKey) {
		this.characterApi = characterApi;
		this.apiKey = apiKey;
	}

	public CharacterFullResponse get(String uid) throws ApiException {
		return characterApi.characterGet(uid, apiKey);
	}

	public CharacterBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String gender, Boolean deceased,
			Boolean hologram, Boolean fictionalCharacter, Boolean mirror, Boolean alternateReality) throws ApiException {
		return characterApi.characterSearchPost(pageNumber, pageSize, sort, apiKey, name, gender, deceased, hologram, fictionalCharacter, mirror,
				alternateReality);
	}

}
