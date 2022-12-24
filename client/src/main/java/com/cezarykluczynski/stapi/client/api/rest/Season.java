package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.SeasonSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.SeasonApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFullResponse;

@SuppressWarnings("ParameterNumber")
public class Season {

	private final SeasonApi seasonApi;

	public Season(SeasonApi seasonApi) {
		this.seasonApi = seasonApi;
	}

	public SeasonFullResponse get(String uid) throws ApiException {
		return seasonApi.v1RestSeasonGet(uid, null);
	}

	@Deprecated
	public SeasonBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer seasonNumberFrom,
			Integer seasonNumberTo, Integer numberOfEpisodesFrom, Integer numberOfEpisodesTo) throws ApiException {
		return seasonApi.v1RestSeasonSearchPost(pageNumber, pageSize, sort, null, title, seasonNumberFrom, seasonNumberTo, numberOfEpisodesFrom,
				numberOfEpisodesTo);
	}

	public SeasonBaseResponse search(SeasonSearchCriteria seasonSearchCriteria) throws ApiException {
		return seasonApi.v1RestSeasonSearchPost(seasonSearchCriteria.getPageNumber(), seasonSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(seasonSearchCriteria.getSort()), null, seasonSearchCriteria.getTitle(),
				seasonSearchCriteria.getSeasonNumberFrom(), seasonSearchCriteria.getSeasonNumberTo(), seasonSearchCriteria.getNumberOfEpisodesFrom(),
				seasonSearchCriteria.getNumberOfEpisodesTo());
	}



}
