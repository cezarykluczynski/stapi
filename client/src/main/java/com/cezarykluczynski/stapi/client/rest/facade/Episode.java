package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.EpisodeApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.EpisodeFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.EpisodeSearchCriteria;

public class Episode {

	private final EpisodeApi episodeApi;

	public Episode(EpisodeApi episodeApi) {
		this.episodeApi = episodeApi;
	}

	public EpisodeFullResponse get(String uid) throws ApiException {
		return episodeApi.v1GetEpisode(uid);
	}

	public EpisodeBaseResponse search(EpisodeSearchCriteria episodeSearchCriteria) throws ApiException {
		return episodeApi.v1SearchEpisodes(episodeSearchCriteria.getPageNumber(), episodeSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(episodeSearchCriteria.getSort()), episodeSearchCriteria.getTitle(),
				episodeSearchCriteria.getSeasonNumberFrom(), episodeSearchCriteria.getSeasonNumberTo(), episodeSearchCriteria.getEpisodeNumberFrom(),
				episodeSearchCriteria.getEpisodeNumberTo(), episodeSearchCriteria.getProductionSerialNumber(),
				episodeSearchCriteria.getFeatureLength(), episodeSearchCriteria.getStardateFrom(), episodeSearchCriteria.getStardateTo(),
				episodeSearchCriteria.getYearFrom(), episodeSearchCriteria.getYearTo(), episodeSearchCriteria.getUsAirDateFrom(),
				episodeSearchCriteria.getUsAirDateTo(), episodeSearchCriteria.getFinalScriptDateFrom(),
				episodeSearchCriteria.getFinalScriptDateTo());
	}

}
