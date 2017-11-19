package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicSeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFullResponse;

@SuppressWarnings("ParameterNumber")
public class ComicSeries {

	private final ComicSeriesApi comicSeriesApi;

	private final String apiKey;

	public ComicSeries(ComicSeriesApi comicSeriesApi, String apiKey) {
		this.comicSeriesApi = comicSeriesApi;
		this.apiKey = apiKey;
	}

	public ComicSeriesFullResponse get(String uid) throws ApiException {
		return comicSeriesApi.comicSeriesGet(uid, apiKey);
	}

	public ComicSeriesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfIssuesFrom, Integer numberOfIssuesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean miniseries, Boolean photonovelSeries) throws ApiException {
		return comicSeriesApi.comicSeriesSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo, numberOfIssuesFrom,
				numberOfIssuesTo, stardateFrom, stardateTo, yearFrom, yearTo, miniseries, photonovelSeries);
	}

}
