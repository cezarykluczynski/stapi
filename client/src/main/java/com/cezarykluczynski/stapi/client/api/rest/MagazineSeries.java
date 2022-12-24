package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.MagazineSeriesSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.MagazineSeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFullResponse;

@SuppressWarnings("ParameterNumber")
public class MagazineSeries {

	private final MagazineSeriesApi magazineSeriesApi;

	public MagazineSeries(MagazineSeriesApi magazineSeriesApi) {
		this.magazineSeriesApi = magazineSeriesApi;
	}

	public MagazineSeriesFullResponse get(String uid) throws ApiException {
		return magazineSeriesApi.v1RestMagazineSeriesGet(uid, null);
	}

	@Deprecated
	public MagazineSeriesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfIssuesFrom, Integer numberOfIssuesTo) throws ApiException {
		return magazineSeriesApi.v1RestMagazineSeriesSearchPost(pageNumber, pageSize, sort, null, title, publishedYearFrom, publishedYearTo,
				numberOfIssuesFrom, numberOfIssuesTo);
	}

	public MagazineSeriesBaseResponse search(MagazineSeriesSearchCriteria magazineSeriesSearchCriteria) throws ApiException {
		return magazineSeriesApi.v1RestMagazineSeriesSearchPost(magazineSeriesSearchCriteria.getPageNumber(),
				magazineSeriesSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(magazineSeriesSearchCriteria.getSort()), null,
				magazineSeriesSearchCriteria.getTitle(), magazineSeriesSearchCriteria.getPublishedYearFrom(),
				magazineSeriesSearchCriteria.getPublishedYearTo(), magazineSeriesSearchCriteria.getNumberOfIssuesFrom(),
				magazineSeriesSearchCriteria.getNumberOfIssuesTo());
	}

}
