package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.SoundtrackApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.SoundtrackBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SoundtrackFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.SoundtrackSearchCriteria;

public class Soundtrack {

	private final SoundtrackApi soundtrackApi;

	public Soundtrack(SoundtrackApi soundtrackApi) {
		this.soundtrackApi = soundtrackApi;
	}

	public SoundtrackFullResponse get(String uid) throws ApiException {
		return soundtrackApi.v1GetSoundtrack(uid);
	}

	public SoundtrackBaseResponse search(SoundtrackSearchCriteria soundtrackSearchCriteria) throws ApiException {
		return soundtrackApi.v1SearchSoundtracks(soundtrackSearchCriteria.getPageNumber(), soundtrackSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(soundtrackSearchCriteria.getSort()), soundtrackSearchCriteria.getTitle(),
				soundtrackSearchCriteria.getReleaseDateFrom(), soundtrackSearchCriteria.getReleaseDateTo(), soundtrackSearchCriteria.getLengthFrom(),
				soundtrackSearchCriteria.getLengthTo());
	}

}
