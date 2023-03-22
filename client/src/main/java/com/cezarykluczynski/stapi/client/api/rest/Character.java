package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.CharacterSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.CharacterApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.CharacterFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.Gender;

@SuppressWarnings("ParameterNumber")
public class Character {

	private final CharacterApi characterApi;


	public Character(CharacterApi characterApi) {
		this.characterApi = characterApi;
	}

	public CharacterFullResponse get(String uid) throws ApiException {
		return characterApi.v1GetCharacter(uid);
	}

	@Deprecated
	public CharacterBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String gender, Boolean deceased,
			Boolean hologram, Boolean fictionalCharacter, Boolean mirror, Boolean alternateReality) throws ApiException {
		return characterApi.v1SearchCharacters(pageNumber, pageSize, sort, name, gender, deceased, hologram, fictionalCharacter,
				mirror, alternateReality);
	}

	public CharacterBaseResponse search(CharacterSearchCriteria characterSearchCriteria) throws ApiException {
		return characterApi.v1SearchCharacters(characterSearchCriteria.getPageNumber(), characterSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(characterSearchCriteria.getSort()), characterSearchCriteria.getName(),
				nameIfPresent(characterSearchCriteria.getGender()), characterSearchCriteria.getDeceased(), characterSearchCriteria.getHologram(),
				characterSearchCriteria.getFictionalCharacter(), characterSearchCriteria.getMirror(), characterSearchCriteria.getAlternateReality());
	}

	private static String nameIfPresent(Gender gender) {
		return gender != null ? gender.name() : null;
	}

}
