package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.SeriesApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SeriesFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.SeriesSearchCriteria;

public class Series {

	private final SeriesApi seriesApi;

	public Series(SeriesApi seriesApi) {
		this.seriesApi = seriesApi;
	}

	public SeriesFullResponse get(String uid) throws ApiException {
		return seriesApi.v1GetSeries(uid);
	}

	public SeriesBaseResponse search(SeriesSearchCriteria seriesSearchCriteria) throws ApiException {
		return seriesApi.v1SearchSeries(seriesSearchCriteria.getPageNumber(), seriesSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(seriesSearchCriteria.getSort()), seriesSearchCriteria.getTitle(),
				seriesSearchCriteria.getAbbreviation(), seriesSearchCriteria.getProductionStartYearFrom(),
				seriesSearchCriteria.getProductionStartYearTo(), seriesSearchCriteria.getProductionEndYearFrom(),
				seriesSearchCriteria.getProductionEndYearTo(), seriesSearchCriteria.getOriginalRunStartDateFrom(),
				seriesSearchCriteria.getOriginalRunStartDateTo(), seriesSearchCriteria.getOriginalRunEndDateFrom(),
				seriesSearchCriteria.getOriginalRunEndDateTo());
	}

}
