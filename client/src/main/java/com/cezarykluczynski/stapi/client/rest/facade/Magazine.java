package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.MagazineApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.MagazineFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.MagazineSearchCriteria;

public class Magazine {

	private final MagazineApi magazineApi;

	public Magazine(MagazineApi magazineApi) {
		this.magazineApi = magazineApi;
	}

	public MagazineFullResponse get(String uid) throws ApiException {
		return magazineApi.v1GetMagazine(uid);
	}

	public MagazineBaseResponse search(MagazineSearchCriteria magazineSearchCriteria) throws ApiException {
		return magazineApi.v1SearchMagazines(magazineSearchCriteria.getPageNumber(), magazineSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(magazineSearchCriteria.getSort()), magazineSearchCriteria.getTitle(),
				magazineSearchCriteria.getPublishedYearFrom(), magazineSearchCriteria.getPublishedYearTo(),
				magazineSearchCriteria.getNumberOfPagesFrom(), magazineSearchCriteria.getNumberOfPagesTo());
	}

}
