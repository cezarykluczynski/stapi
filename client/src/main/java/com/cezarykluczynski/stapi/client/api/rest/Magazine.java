package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.MagazineApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFullResponse;

@SuppressWarnings("ParameterNumber")
public class Magazine {

	private final MagazineApi magazineApi;

	private final String apiKey;

	public Magazine(MagazineApi magazineApi, String apiKey) {
		this.magazineApi = magazineApi;
		this.apiKey = apiKey;
	}

	public MagazineFullResponse get(String uid) throws ApiException {
		return magazineApi.magazineGet(uid, apiKey);
	}

	public MagazineBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo) throws ApiException {
		return magazineApi.magazineSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo);
	}

}
