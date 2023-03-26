package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.ComicStripApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicStripFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicStripSearchCriteria;

public class ComicStrip {

	private final ComicStripApi comicStripApi;

	public ComicStrip(ComicStripApi comicStripApi) {
		this.comicStripApi = comicStripApi;
	}

	public ComicStripFullResponse get(String uid) throws ApiException {
		return comicStripApi.v1GetComicStrip(uid);
	}

	public ComicStripBaseResponse search(ComicStripSearchCriteria comicStripSearchCriteria) throws ApiException {
		return comicStripApi.v1SearchComicStrips(comicStripSearchCriteria.getPageNumber(), comicStripSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(comicStripSearchCriteria.getSort()), comicStripSearchCriteria.getTitle(),
				comicStripSearchCriteria.getPublishedYearFrom(), comicStripSearchCriteria.getPublishedYearTo(),
				comicStripSearchCriteria.getNumberOfPagesFrom(), comicStripSearchCriteria.getNumberOfPagesTo(),
				comicStripSearchCriteria.getYearFrom(), comicStripSearchCriteria.getYearTo());
	}

}
