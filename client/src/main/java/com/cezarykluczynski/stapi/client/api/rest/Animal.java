package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.AnimalApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFullResponse;

@SuppressWarnings("ParameterNumber")
public class Animal {

	private final AnimalApi animalApi;

	private final String apiKey;

	public Animal(AnimalApi animalApi, String apiKey) {
		this.animalApi = animalApi;
		this.apiKey = apiKey;
	}

	public AnimalFullResponse get(String uid) throws ApiException {
		return animalApi.animalGet(uid, apiKey);
	}

	public AnimalBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean earthAnimal, Boolean earthInsect,
			Boolean avian, Boolean canine, Boolean feline) throws ApiException {
		return animalApi.animalSearchPost(pageNumber, pageSize, sort, apiKey, name, earthAnimal, earthInsect, avian, canine, feline);
	}

}
