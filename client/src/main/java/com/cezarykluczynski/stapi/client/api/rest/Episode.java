package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.EpisodeApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Episode {

	private final EpisodeApi episodeApi;

	private final String apiKey;

	public Episode(EpisodeApi episodeApi, String apiKey) {
		this.episodeApi = episodeApi;
		this.apiKey = apiKey;
	}

	public EpisodeFullResponse get(String uid) throws ApiException {
		return episodeApi.episodeGet(uid, apiKey);
	}

	public EpisodeBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer seasonNumberFrom,
			Integer seasonNumberTo, Integer episodeNumberFrom, Integer episodeNumberTo, String productionSerialNumber, Boolean featureLength,
			Float stardateFrom, Float stardateTo, Integer yearFrom, Integer yearTo, LocalDate usAirDateFrom, LocalDate usAirDateTo,
			LocalDate finalScriptDateFrom, LocalDate finalScriptDateTo) throws ApiException {
		return episodeApi.episodeSearchPost(pageNumber, pageSize, sort, apiKey, title, seasonNumberFrom, seasonNumberTo, episodeNumberFrom,
				episodeNumberTo, productionSerialNumber, featureLength, stardateFrom, stardateTo, yearFrom, yearTo, usAirDateFrom, usAirDateTo,
				finalScriptDateFrom, finalScriptDateTo);
	}

}
