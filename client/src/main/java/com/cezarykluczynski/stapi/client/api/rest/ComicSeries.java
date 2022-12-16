package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicSeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFullResponse;

@SuppressWarnings("ParameterNumber")
public class ComicSeries {

	private final ComicSeriesApi comicSeriesApi;

	public ComicSeries(ComicSeriesApi comicSeriesApi) {
		this.comicSeriesApi = comicSeriesApi;
	}

	public ComicSeriesFullResponse get(String uid) throws ApiException {
		return comicSeriesApi.v1RestComicSeriesGet(uid, null);
	}

	public ComicSeriesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfIssuesFrom, Integer numberOfIssuesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean miniseries, Boolean photonovelSeries) throws ApiException {
		return comicSeriesApi.v1RestComicSeriesSearchPost(pageNumber, pageSize, sort, null, title, publishedYearFrom, publishedYearTo,
				numberOfIssuesFrom, numberOfIssuesTo, stardateFrom, stardateTo, yearFrom, yearTo, miniseries, photonovelSeries);
	}

}
