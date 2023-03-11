package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.PerformerV2SearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.PerformerApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.Gender;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2FullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Performer {

	private final PerformerApi performerApi;

	public Performer(PerformerApi performerApi) {
		this.performerApi = performerApi;
	}

	@Deprecated
	public PerformerFullResponse get(String uid) throws ApiException {
		return performerApi.v1RestPerformerGet(uid);
	}

	public PerformerV2FullResponse getV2(String uid) throws ApiException {
		return performerApi.v2RestPerformerGet(uid);
	}

	@Deprecated
	public PerformerBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String birthName, String gender,
			LocalDate dateOfBirthFrom, LocalDate dateOfBirthTo, String placeOfBirth, LocalDate dateOfDeathFrom, LocalDate dateOfDeathTo,
			String placeOfDeath, Boolean animalPerformer, Boolean disPerformer, Boolean ds9Performer, Boolean entPerformer, Boolean filmPerformer,
			Boolean standInPerformer, Boolean stuntPerformer, Boolean tasPerformer, Boolean tngPerformer, Boolean tosPerformer,
			Boolean videoGamePerformer, Boolean voicePerformer, Boolean voyPerformer) throws ApiException {
		return performerApi.v1RestPerformerSearchPost(pageNumber, pageSize, sort, name, birthName, gender, dateOfBirthFrom, dateOfBirthTo,
				placeOfBirth, dateOfDeathFrom, dateOfDeathTo, placeOfDeath, animalPerformer, disPerformer, ds9Performer, entPerformer, filmPerformer,
				standInPerformer, stuntPerformer, tasPerformer, tngPerformer, tosPerformer, videoGamePerformer, voicePerformer, voyPerformer);
	}

	@Deprecated
	public PerformerV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, String birthName, String gender,
			LocalDate dateOfBirthFrom, LocalDate dateOfBirthTo, String placeOfBirth, LocalDate dateOfDeathFrom, LocalDate dateOfDeathTo,
			String placeOfDeath, Boolean animalPerformer, Boolean audiobookPerformer, Boolean cutPerformer, Boolean disPerformer,
			Boolean ds9Performer, Boolean entPerformer, Boolean filmPerformer, Boolean ldPerformer, Boolean picPerformer,
			Boolean proPerformer, Boolean puppeteer, Boolean snwPerformer, Boolean standInPerformer, Boolean stPerformer,
			Boolean stuntPerformer, Boolean tasPerformer, Boolean tngPerformer, Boolean tosPerformer, Boolean videoGamePerformer,
			Boolean voicePerformer, Boolean voyPerformer) throws ApiException {
		return performerApi.v2RestPerformerSearchPost(pageNumber, pageSize, sort, name, birthName, gender, dateOfBirthFrom, dateOfBirthTo,
				placeOfBirth, dateOfDeathFrom, dateOfDeathTo, placeOfDeath, animalPerformer, audiobookPerformer, cutPerformer, disPerformer,
				ds9Performer, entPerformer, filmPerformer, ldPerformer, picPerformer, proPerformer, puppeteer, snwPerformer, standInPerformer,
				stPerformer, stuntPerformer, tasPerformer, tngPerformer, tosPerformer, videoGamePerformer, voicePerformer, voyPerformer);
	}

	public PerformerV2BaseResponse searchV2(PerformerV2SearchCriteria performerV2SearchCriteria) throws ApiException {
		return performerApi.v2RestPerformerSearchPost(performerV2SearchCriteria.getPageNumber(), performerV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(performerV2SearchCriteria.getSort()), performerV2SearchCriteria.getName(),
				performerV2SearchCriteria.getBirthName(), nameIfPresent(performerV2SearchCriteria.getGender()),
				performerV2SearchCriteria.getDateOfBirthFrom(), performerV2SearchCriteria.getDateOfBirthTo(),
				performerV2SearchCriteria.getPlaceOfBirth(), performerV2SearchCriteria.getDateOfDeathFrom(),
				performerV2SearchCriteria.getDateOfDeathTo(), performerV2SearchCriteria.getPlaceOfDeath(),
				performerV2SearchCriteria.getAnimalPerformer(), performerV2SearchCriteria.getAudiobookPerformer(),
				performerV2SearchCriteria.getCutPerformer(), performerV2SearchCriteria.getDisPerformer(), performerV2SearchCriteria.getDs9Performer(),
				performerV2SearchCriteria.getEntPerformer(), performerV2SearchCriteria.getFilmPerformer(), performerV2SearchCriteria.getLdPerformer(),
				performerV2SearchCriteria.getPicPerformer(), performerV2SearchCriteria.getProPerformer(), performerV2SearchCriteria.getPuppeteer(),
				performerV2SearchCriteria.getSnwPerformer(), performerV2SearchCriteria.getStandInPerformer(),
				performerV2SearchCriteria.getStPerformer(), performerV2SearchCriteria.getStuntPerformer(),
				performerV2SearchCriteria.getTasPerformer(), performerV2SearchCriteria.getTngPerformer(), performerV2SearchCriteria.getTosPerformer(),
				performerV2SearchCriteria.getVideoGamePerformer(), performerV2SearchCriteria.getVoicePerformer(),
				performerV2SearchCriteria.getVoyPerformer());
	}

	private static String nameIfPresent(Gender gender) {
		return gender != null ? gender.name() : null;
	}

}
