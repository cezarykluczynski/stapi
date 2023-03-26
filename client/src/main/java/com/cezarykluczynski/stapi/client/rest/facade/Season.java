package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.SeasonApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.SeasonBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SeasonFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.SeasonSearchCriteria;

public class Season {

	private final SeasonApi seasonApi;

	public Season(SeasonApi seasonApi) {
		this.seasonApi = seasonApi;
	}

	public SeasonFullResponse get(String uid) throws ApiException {
		return seasonApi.v1GetSeason(uid);
	}

	public SeasonBaseResponse search(SeasonSearchCriteria seasonSearchCriteria) throws ApiException {
		return seasonApi.v1SearchSeasons(seasonSearchCriteria.getPageNumber(), seasonSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(seasonSearchCriteria.getSort()), seasonSearchCriteria.getTitle(),
				seasonSearchCriteria.getSeasonNumberFrom(), seasonSearchCriteria.getSeasonNumberTo(), seasonSearchCriteria.getNumberOfEpisodesFrom(),
				seasonSearchCriteria.getNumberOfEpisodesTo());
	}

}
