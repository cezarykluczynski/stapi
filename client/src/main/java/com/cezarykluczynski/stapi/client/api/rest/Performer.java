package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.PerformerApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Performer {

	private final PerformerApi performerApi;

	private final String apiKey;

	public Performer(PerformerApi performerApi, String apiKey) {
		this.performerApi = performerApi;
		this.apiKey = apiKey;
	}

	public PerformerFullResponse get(String uid) throws ApiException {
		return performerApi.performerGet(uid, apiKey);
	}

	public PerformerBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String birthName, String gender,
			LocalDate dateOfBirthFrom, LocalDate dateOfBirthTo, String placeOfBirth, LocalDate dateOfDeathFrom, LocalDate dateOfDeathTo,
			String placeOfDeath, Boolean animalPerformer, Boolean disPerformer, Boolean ds9Performer, Boolean entPerformer, Boolean filmPerformer,
			Boolean standInPerformer, Boolean stuntPerformer, Boolean tasPerformer, Boolean tngPerformer, Boolean tosPerformer,
			Boolean videoGamePerformer, Boolean voicePerformer, Boolean voyPerformer) throws ApiException {
		return performerApi.performerSearchPost(pageNumber, pageSize, sort, apiKey, name, birthName, gender, dateOfBirthFrom, dateOfBirthTo,
				placeOfBirth, dateOfDeathFrom, dateOfDeathTo, placeOfDeath, animalPerformer, disPerformer, ds9Performer, entPerformer, filmPerformer,
				standInPerformer, stuntPerformer, tasPerformer, tngPerformer, tosPerformer, videoGamePerformer, voicePerformer, voyPerformer);
	}

}
