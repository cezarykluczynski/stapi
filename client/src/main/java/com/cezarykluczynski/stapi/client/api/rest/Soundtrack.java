package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SoundtrackApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Soundtrack {

	private final SoundtrackApi soundtrackApi;

	public Soundtrack(SoundtrackApi soundtrackApi) {
		this.soundtrackApi = soundtrackApi;
	}

	public SoundtrackFullResponse get(String uid) throws ApiException {
		return soundtrackApi.v1RestSoundtrackGet(uid, null);
	}

	public SoundtrackBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, LocalDate releaseDateFrom,
			LocalDate releaseDateTo, Integer lengthFrom, Integer lengthTo) throws ApiException {
		return soundtrackApi.v1RestSoundtrackSearchPost(pageNumber, pageSize, sort, null, title, releaseDateFrom, releaseDateTo, lengthFrom,
				lengthTo);
	}

}
