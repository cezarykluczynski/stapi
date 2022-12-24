package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.MovieSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.MovieApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Movie {

	private final MovieApi movieApi;

	public Movie(MovieApi movieApi) {
		this.movieApi = movieApi;
	}

	public MovieFullResponse get(String uid) throws ApiException {
		return movieApi.v1RestMovieGet(uid, null);
	}

	@Deprecated
	public MovieBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Float stardateFrom, Float stardateTo,
			Integer yearFrom, Integer yearTo, LocalDate usReleaseDateFrom, LocalDate usReleaseDateTo) throws ApiException {
		return movieApi.v1RestMovieSearchPost(pageNumber, pageSize, sort, null, title, stardateFrom, stardateTo, yearFrom, yearTo,
				usReleaseDateFrom, usReleaseDateTo);
	}

	public MovieBaseResponse search(MovieSearchCriteria movieSearchCriteria) throws ApiException {
		return movieApi.v1RestMovieSearchPost(movieSearchCriteria.getPageNumber(), movieSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(movieSearchCriteria.getSort()), null, movieSearchCriteria.getTitle(),
				movieSearchCriteria.getStardateFrom(), movieSearchCriteria.getStardateTo(), movieSearchCriteria.getYearFrom(),
				movieSearchCriteria.getYearTo(), movieSearchCriteria.getUsReleaseDateFrom(), movieSearchCriteria.getUsReleaseDateTo());
	}

}
