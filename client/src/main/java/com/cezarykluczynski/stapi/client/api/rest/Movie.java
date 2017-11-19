package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.MovieApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Movie {

	private final MovieApi movieApi;

	private final String apiKey;

	public Movie(MovieApi movieApi, String apiKey) {
		this.movieApi = movieApi;
		this.apiKey = apiKey;
	}

	public MovieFullResponse get(String uid) throws ApiException {
		return movieApi.movieGet(uid, apiKey);
	}

	public MovieBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Float stardateFrom, Float stardateTo,
			Integer yearFrom, Integer yearTo, LocalDate usReleaseDateFrom, LocalDate usReleaseDateTo) throws ApiException {
		return movieApi.movieSearchPost(pageNumber, pageSize, sort, apiKey, title, stardateFrom, stardateTo, yearFrom, yearTo, usReleaseDateFrom,
				usReleaseDateTo);
	}

}
