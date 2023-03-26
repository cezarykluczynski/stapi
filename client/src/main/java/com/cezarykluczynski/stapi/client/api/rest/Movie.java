package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.MovieApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.MovieFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.MovieSearchCriteria;

public class Movie {

	private final MovieApi movieApi;

	public Movie(MovieApi movieApi) {
		this.movieApi = movieApi;
	}

	public MovieFullResponse get(String uid) throws ApiException {
		return movieApi.v1GetMovie(uid);
	}

	public MovieBaseResponse search(MovieSearchCriteria movieSearchCriteria) throws ApiException {
		return movieApi.v1SearchMovies(movieSearchCriteria.getPageNumber(), movieSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(movieSearchCriteria.getSort()), movieSearchCriteria.getTitle(),
				movieSearchCriteria.getStardateFrom(), movieSearchCriteria.getStardateTo(), movieSearchCriteria.getYearFrom(),
				movieSearchCriteria.getYearTo(), movieSearchCriteria.getUsReleaseDateFrom(), movieSearchCriteria.getUsReleaseDateTo());
	}

}
