package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.PerformerApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2SearchCriteria;

public class Performer {

	private final PerformerApi performerApi;

	public Performer(PerformerApi performerApi) {
		this.performerApi = performerApi;
	}

	public PerformerV2FullResponse getV2(String uid) throws ApiException {
		return performerApi.v2GetPerformer(uid);
	}

	public PerformerV2BaseResponse searchV2(PerformerV2SearchCriteria performerV2SearchCriteria) throws ApiException {
		return performerApi.v2SearchPerformers(performerV2SearchCriteria.getPageNumber(), performerV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(performerV2SearchCriteria.getSort()), performerV2SearchCriteria.getName(),
				performerV2SearchCriteria.getBirthName(), performerV2SearchCriteria.getGender(),
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

}
