package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.SeriesSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.SeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Series {

	private final SeriesApi seriesApi;

	public Series(SeriesApi seriesApi) {
		this.seriesApi = seriesApi;
	}

	public SeriesFullResponse get(String uid) throws ApiException {
		return seriesApi.v1RestSeriesGet(uid);
	}

	@Deprecated
	public SeriesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, String abbreviation,
			Integer productionStartYearFrom, Integer productionStartYearTo, Integer productionEndYearFrom, Integer productionEndYearTo,
			LocalDate originalRunStartDateFrom, LocalDate originalRunStartDateTo, LocalDate originalRunEndDateFrom, LocalDate originalRunEndDateTo)
			throws ApiException {
		return seriesApi.v1RestSeriesSearchPost(pageNumber, pageSize, sort, title, abbreviation, productionStartYearFrom,
				productionStartYearTo, productionEndYearFrom, productionEndYearTo, originalRunStartDateFrom, originalRunStartDateTo,
				originalRunEndDateFrom, originalRunEndDateTo);
	}

	public SeriesBaseResponse search(SeriesSearchCriteria seriesSearchCriteria) throws ApiException {
		return seriesApi.v1RestSeriesSearchPost(seriesSearchCriteria.getPageNumber(), seriesSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(seriesSearchCriteria.getSort()), seriesSearchCriteria.getTitle(),
				seriesSearchCriteria.getAbbreviation(), seriesSearchCriteria.getProductionStartYearFrom(),
				seriesSearchCriteria.getProductionStartYearTo(), seriesSearchCriteria.getProductionEndYearFrom(),
				seriesSearchCriteria.getProductionEndYearTo(), seriesSearchCriteria.getOriginalRunStartDateFrom(),
				seriesSearchCriteria.getOriginalRunStartDateTo(), seriesSearchCriteria.getOriginalRunEndDateFrom(),
				seriesSearchCriteria.getOriginalRunEndDateTo());
	}

}
