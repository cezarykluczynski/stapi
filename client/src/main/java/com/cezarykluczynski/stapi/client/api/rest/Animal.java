package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.AnimalSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.AnimalApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.AnimalFullResponse;

@SuppressWarnings("ParameterNumber")
public class Animal {

	private final AnimalApi animalApi;

	public Animal(AnimalApi animalApi) {
		this.animalApi = animalApi;
	}

	public AnimalFullResponse get(String uid) throws ApiException {
		return animalApi.v1Get(uid);
	}

	@Deprecated
	public AnimalBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean earthAnimal, Boolean earthInsect,
			Boolean avian, Boolean canine, Boolean feline) throws ApiException {
		return animalApi.v1Search(pageNumber, pageSize, sort, name, earthAnimal, earthInsect, avian, canine, feline);
	}

	public AnimalBaseResponse search(AnimalSearchCriteria animalSearchCriteria) throws ApiException {
		return animalApi.v1Search(animalSearchCriteria.getPageNumber(), animalSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(animalSearchCriteria.getSort()), animalSearchCriteria.getName(),
						animalSearchCriteria.getEarthAnimal(), animalSearchCriteria.getEarthInsect(), animalSearchCriteria.getAvian(),
						animalSearchCriteria.getCanine(), animalSearchCriteria.getFeline());
	}

}
