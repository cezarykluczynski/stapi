package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.ComicSeriesApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesSearchCriteria;

public class ComicSeries {

	private final ComicSeriesApi comicSeriesApi;

	public ComicSeries(ComicSeriesApi comicSeriesApi) {
		this.comicSeriesApi = comicSeriesApi;
	}

	public ComicSeriesFullResponse get(String uid) throws ApiException {
		return comicSeriesApi.v1GetComicSeries(uid);
	}

	public ComicSeriesBaseResponse search(ComicSeriesSearchCriteria comicSeriesSearchCriteria) throws ApiException {
		return comicSeriesApi.v1SearchComicSeries(comicSeriesSearchCriteria.getPageNumber(), comicSeriesSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(comicSeriesSearchCriteria.getSort()), comicSeriesSearchCriteria.getTitle(),
				comicSeriesSearchCriteria.getPublishedYearFrom(), comicSeriesSearchCriteria.getPublishedYearTo(),
				comicSeriesSearchCriteria.getNumberOfIssuesFrom(), comicSeriesSearchCriteria.getNumberOfIssuesTo(),
				comicSeriesSearchCriteria.getStardateFrom(), comicSeriesSearchCriteria.getStardateTo(), comicSeriesSearchCriteria.getYearFrom(),
				comicSeriesSearchCriteria.getYearTo(), comicSeriesSearchCriteria.getMiniseries(), comicSeriesSearchCriteria.getPhotonovelSeries());
	}

}
