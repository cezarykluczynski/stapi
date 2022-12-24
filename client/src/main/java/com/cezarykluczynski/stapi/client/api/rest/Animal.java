package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.AnimalSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.AnimalApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFullResponse;

@SuppressWarnings("ParameterNumber")
public class Animal {

	private final AnimalApi animalApi;

	public Animal(AnimalApi animalApi) {
		this.animalApi = animalApi;
	}

	public AnimalFullResponse get(String uid) throws ApiException {
		return animalApi.v1RestAnimalGet(uid, null);
	}

	@Deprecated
	public AnimalBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean earthAnimal, Boolean earthInsect,
			Boolean avian, Boolean canine, Boolean feline) throws ApiException {
		return animalApi.v1RestAnimalSearchPost(pageNumber, pageSize, sort, null, name, earthAnimal, earthInsect, avian, canine, feline);
	}

	public AnimalBaseResponse search(AnimalSearchCriteria animalSearchCriteria) throws ApiException {
		return animalApi.v1RestAnimalSearchPost(animalSearchCriteria.getPageNumber(), animalSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(animalSearchCriteria.getSort()), null, animalSearchCriteria.getName(),
						animalSearchCriteria.getEarthAnimal(), animalSearchCriteria.getEarthInsect(), animalSearchCriteria.getAvian(),
						animalSearchCriteria.getCanine(), animalSearchCriteria.getFeline());
	}

}
