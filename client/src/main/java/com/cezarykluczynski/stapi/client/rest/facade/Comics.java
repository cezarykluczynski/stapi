package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.ComicsApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicsFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicsSearchCriteria;

public class Comics {

	private final ComicsApi comicsApi;

	public Comics(ComicsApi comicsApi) {
		this.comicsApi = comicsApi;
	}

	public ComicsFullResponse get(String uid) throws ApiException {
		return comicsApi.v1GetComics(uid);
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
