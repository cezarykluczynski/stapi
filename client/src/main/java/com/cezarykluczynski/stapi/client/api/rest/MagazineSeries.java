package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.MagazineSeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFullResponse;

@SuppressWarnings("ParameterNumber")
public class MagazineSeries {

	private final MagazineSeriesApi magazineSeriesApi;

	private final String apiKey;

	public MagazineSeries(MagazineSeriesApi magazineSeriesApi, String apiKey) {
		this.magazineSeriesApi = magazineSeriesApi;
		this.apiKey = apiKey;
	}

	public MagazineSeriesFullResponse get(String uid) throws ApiException {
		return magazineSeriesApi.v1RestMagazineSeriesGet(uid, apiKey);
	}

	public MagazineSeriesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfIssuesFrom, Integer numberOfIssuesTo) throws ApiException {
		return magazineSeriesApi.v1RestMagazineSeriesSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo,
				numberOfIssuesFrom, numberOfIssuesTo);
	}
}
