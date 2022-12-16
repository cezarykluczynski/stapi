package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicStripApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse;

@SuppressWarnings("ParameterNumber")
public class ComicStrip {

	private ComicStripApi comicStripApi;

	public ComicStrip(ComicStripApi comicStripApi) {
		this.comicStripApi = comicStripApi;
	}

	public ComicStripFullResponse get(String uid) throws ApiException {
		return comicStripApi.v1RestComicStripGet(uid, null);
	}

	public ComicStripBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Integer yearFrom, Integer yearTo) throws ApiException {
		return comicStripApi.v1RestComicStripSearchPost(pageNumber, pageSize, sort, null, title, publishedYearFrom, publishedYearTo,
				numberOfPagesFrom, numberOfPagesTo, yearFrom, yearTo);
	}

}
