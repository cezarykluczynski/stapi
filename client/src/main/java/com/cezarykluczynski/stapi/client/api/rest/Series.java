package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Series {

	private final SeriesApi seriesApi;

	private final String apiKey;

	public Series(SeriesApi seriesApi, String apiKey) {
		this.seriesApi = seriesApi;
		this.apiKey = apiKey;
	}

	public SeriesFullResponse get(String uid) throws ApiException {
		return seriesApi.seriesGet(uid, apiKey);
	}

	public SeriesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, String abbreviation,
			Integer productionStartYearFrom, Integer productionStartYearTo, Integer productionEndYearFrom, Integer productionEndYearTo,
			LocalDate originalRunStartDateFrom, LocalDate originalRunStartDateTo, LocalDate originalRunEndDateFrom, LocalDate originalRunEndDateTo)
			throws ApiException {
		return seriesApi.seriesSearchPost(pageNumber, pageSize, sort, apiKey, title, abbreviation, productionStartYearFrom, productionStartYearTo,
				productionEndYearFrom, productionEndYearTo, originalRunStartDateFrom, originalRunStartDateTo, originalRunEndDateFrom,
				originalRunEndDateTo);
	}

}
