package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.AnimalApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.AnimalFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.AnimalSearchCriteria;

public class Animal {

	private final AnimalApi animalApi;

	public Animal(AnimalApi animalApi) {
		this.animalApi = animalApi;
	}

	public AnimalFullResponse get(String uid) throws ApiException {
		return animalApi.v1GetAnimal(uid);
	}

	public AnimalBaseResponse search(AnimalSearchCriteria animalSearchCriteria) throws ApiException {
		return animalApi.v1SearchAnimals(animalSearchCriteria.getPageNumber(), animalSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(animalSearchCriteria.getSort()), animalSearchCriteria.getName(),
						animalSearchCriteria.getEarthAnimal(), animalSearchCriteria.getEarthInsect(), animalSearchCriteria.getAvian(),
						animalSearchCriteria.getCanine(), animalSearchCriteria.getFeline());
	}

}
