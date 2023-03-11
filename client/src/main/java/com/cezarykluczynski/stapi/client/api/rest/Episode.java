package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.EpisodeSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.EpisodeApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.EpisodeFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Episode {

	private final EpisodeApi episodeApi;

	public Episode(EpisodeApi episodeApi) {
		this.episodeApi = episodeApi;
	}

	public EpisodeFullResponse get(String uid) throws ApiException {
		return episodeApi.v1RestEpisodeGet(uid);
	}

	@Deprecated
	public EpisodeBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer seasonNumberFrom,
			Integer seasonNumberTo, Integer episodeNumberFrom, Integer episodeNumberTo, String productionSerialNumber, Boolean featureLength,
			Float stardateFrom, Float stardateTo, Integer yearFrom, Integer yearTo, LocalDate usAirDateFrom, LocalDate usAirDateTo,
			LocalDate finalScriptDateFrom, LocalDate finalScriptDateTo) throws ApiException {
		return episodeApi.v1RestEpisodeSearchPost(pageNumber, pageSize, sort, title, seasonNumberFrom, seasonNumberTo, episodeNumberFrom,
				episodeNumberTo, productionSerialNumber, featureLength, stardateFrom, stardateTo, yearFrom, yearTo, usAirDateFrom, usAirDateTo,
				finalScriptDateFrom, finalScriptDateTo);
	}

	public EpisodeBaseResponse search(EpisodeSearchCriteria episodeSearchCriteria) throws ApiException {
		return episodeApi.v1RestEpisodeSearchPost(episodeSearchCriteria.getPageNumber(), episodeSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(episodeSearchCriteria.getSort()), episodeSearchCriteria.getTitle(),
				episodeSearchCriteria.getSeasonNumberFrom(), episodeSearchCriteria.getSeasonNumberTo(), episodeSearchCriteria.getEpisodeNumberFrom(),
				episodeSearchCriteria.getEpisodeNumberTo(), episodeSearchCriteria.getProductionSerialNumber(),
				episodeSearchCriteria.getFeatureLength(), episodeSearchCriteria.getStardateFrom(), episodeSearchCriteria.getStardateTo(),
				episodeSearchCriteria.getYearFrom(), episodeSearchCriteria.getYearTo(), episodeSearchCriteria.getUsAirDateFrom(),
				episodeSearchCriteria.getUsAirDateTo(), episodeSearchCriteria.getFinalScriptDateFrom(),
				episodeSearchCriteria.getFinalScriptDateTo());
	}

}
