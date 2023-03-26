package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.MagazineSeriesApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.MagazineSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.MagazineSeriesFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.MagazineSeriesSearchCriteria;

public class MagazineSeries {

	private final MagazineSeriesApi magazineSeriesApi;

	public MagazineSeries(MagazineSeriesApi magazineSeriesApi) {
		this.magazineSeriesApi = magazineSeriesApi;
	}

	public MagazineSeriesFullResponse get(String uid) throws ApiException {
		return magazineSeriesApi.v1GetMagazineSeries(uid);
	}

	public MagazineSeriesBaseResponse search(MagazineSeriesSearchCriteria magazineSeriesSearchCriteria) throws ApiException {
		return magazineSeriesApi.v1SearchMagazineSeries(magazineSeriesSearchCriteria.getPageNumber(),
				magazineSeriesSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(magazineSeriesSearchCriteria.getSort()),
				magazineSeriesSearchCriteria.getTitle(), magazineSeriesSearchCriteria.getPublishedYearFrom(),
				magazineSeriesSearchCriteria.getPublishedYearTo(), magazineSeriesSearchCriteria.getNumberOfIssuesFrom(),
				magazineSeriesSearchCriteria.getNumberOfIssuesTo());
	}

}
