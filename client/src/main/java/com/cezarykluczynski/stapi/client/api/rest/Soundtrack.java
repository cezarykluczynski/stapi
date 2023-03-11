package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.SoundtrackSearchCriteria;
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
		return soundtrackApi.v1RestSoundtrackGet(uid);
	}

	public SoundtrackBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, LocalDate releaseDateFrom,
			LocalDate releaseDateTo, Integer lengthFrom, Integer lengthTo) throws ApiException {
		return soundtrackApi.v1RestSoundtrackSearchPost(pageNumber, pageSize, sort, title, releaseDateFrom, releaseDateTo, lengthFrom,
				lengthTo);
	}

	public SoundtrackBaseResponse search(SoundtrackSearchCriteria soundtrackSearchCriteria) throws ApiException {
		return soundtrackApi.v1RestSoundtrackSearchPost(soundtrackSearchCriteria.getPageNumber(), soundtrackSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(soundtrackSearchCriteria.getSort()), soundtrackSearchCriteria.getTitle(),
				soundtrackSearchCriteria.getReleaseDateFrom(), soundtrackSearchCriteria.getReleaseDateTo(), soundtrackSearchCriteria.getLengthFrom(),
				soundtrackSearchCriteria.getLengthTo());
	}

}
