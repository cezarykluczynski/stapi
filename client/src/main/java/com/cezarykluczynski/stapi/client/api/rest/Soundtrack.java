package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SoundtrackApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Soundtrack {

	private final SoundtrackApi soundtrackApi;

	private final String apiKey;

	public Soundtrack(SoundtrackApi soundtrackApi, String apiKey) {
		this.soundtrackApi = soundtrackApi;
		this.apiKey = apiKey;
	}

	public SoundtrackFullResponse get(String uid) throws ApiException {
		return soundtrackApi.soundtrackGet(uid, apiKey);
	}

	public SoundtrackBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, LocalDate releaseDateFrom,
			LocalDate releaseDateTo, Integer lengthFrom, Integer lengthTo) throws ApiException {
		return soundtrackApi.soundtrackSearchPost(pageNumber, pageSize, sort, apiKey, title, releaseDateFrom, releaseDateTo, lengthFrom, lengthTo);
	}

}
