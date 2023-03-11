package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.ComicSeriesSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.ComicSeriesApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesFullResponse;

@SuppressWarnings("ParameterNumber")
public class ComicSeries {

	private final ComicSeriesApi comicSeriesApi;

	public ComicSeries(ComicSeriesApi comicSeriesApi) {
		this.comicSeriesApi = comicSeriesApi;
	}

	public ComicSeriesFullResponse get(String uid) throws ApiException {
		return comicSeriesApi.v1RestComicSeriesGet(uid);
	}

	@Deprecated
	public ComicSeriesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfIssuesFrom, Integer numberOfIssuesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean miniseries, Boolean photonovelSeries) throws ApiException {
		return comicSeriesApi.v1RestComicSeriesSearchPost(pageNumber, pageSize, sort, title, publishedYearFrom, publishedYearTo,
				numberOfIssuesFrom, numberOfIssuesTo, stardateFrom, stardateTo, yearFrom, yearTo, miniseries, photonovelSeries);
	}

	public ComicSeriesBaseResponse search(ComicSeriesSearchCriteria comicSeriesSearchCriteria) throws ApiException {
		return comicSeriesApi.v1RestComicSeriesSearchPost(comicSeriesSearchCriteria.getPageNumber(), comicSeriesSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(comicSeriesSearchCriteria.getSort()), comicSeriesSearchCriteria.getTitle(),
				comicSeriesSearchCriteria.getPublishedYearFrom(), comicSeriesSearchCriteria.getPublishedYearTo(),
				comicSeriesSearchCriteria.getNumberOfIssuesFrom(), comicSeriesSearchCriteria.getNumberOfIssuesTo(),
				comicSeriesSearchCriteria.getStardateFrom(), comicSeriesSearchCriteria.getStardateTo(), comicSeriesSearchCriteria.getYearFrom(),
				comicSeriesSearchCriteria.getYearTo(), comicSeriesSearchCriteria.getMiniseries(), comicSeriesSearchCriteria.getPhotonovelSeries());
	}

}
