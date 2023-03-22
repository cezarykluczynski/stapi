package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.ComicsSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.ComicsApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicsFullResponse;

@SuppressWarnings("ParameterNumber")
public class Comics {

	private final ComicsApi comicsApi;

	public Comics(ComicsApi comicsApi) {
		this.comicsApi = comicsApi;
	}

	public ComicsFullResponse get(String uid) throws ApiException {
		return comicsApi.v1GetComics(uid);
	}

	@Deprecated
	public ComicsBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean photonovel, Boolean adaptation) throws ApiException {
		return comicsApi.v1SearchComics(pageNumber, pageSize, sort, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, photonovel, adaptation);
	}

	public ComicsBaseResponse search(ComicsSearchCriteria comicsSearchCriteria) throws ApiException {
		return comicsApi.v1SearchComics(comicsSearchCriteria.getPageNumber(), comicsSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(comicsSearchCriteria.getSort()), comicsSearchCriteria.getTitle(),
				comicsSearchCriteria.getPublishedYearFrom(), comicsSearchCriteria.getPublishedYearTo(), comicsSearchCriteria.getNumberOfPagesFrom(),
				comicsSearchCriteria.getNumberOfPagesTo(), comicsSearchCriteria.getStardateFrom(), comicsSearchCriteria.getStardateTo(),
				comicsSearchCriteria.getYearFrom(), comicsSearchCriteria.getYearTo(), comicsSearchCriteria.getPhotonovel(),
				comicsSearchCriteria.getAdaptation());
	}

}
