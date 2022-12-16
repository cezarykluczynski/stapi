package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.MagazineApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFullResponse;

@SuppressWarnings("ParameterNumber")
public class Magazine {

	private final MagazineApi magazineApi;

	public Magazine(MagazineApi magazineApi) {
		this.magazineApi = magazineApi;
	}

	public MagazineFullResponse get(String uid) throws ApiException {
		return magazineApi.v1RestMagazineGet(uid, null);
	}

	public MagazineBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo) throws ApiException {
		return magazineApi.v1RestMagazineSearchPost(pageNumber, pageSize, sort, null, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo);
	}

}
