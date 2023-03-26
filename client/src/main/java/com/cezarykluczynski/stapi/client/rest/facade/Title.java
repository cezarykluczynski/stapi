package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.TitleApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.TitleV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TitleV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.TitleV2SearchCriteria;

public class Title {

	private final TitleApi titleApi;

	public Title(TitleApi titleApi) {
		this.titleApi = titleApi;
	}

	public TitleV2FullResponse getV2(String uid) throws ApiException {
		return titleApi.v2GetTitle(uid);
	}

	public TitleV2BaseResponse searchV2(TitleV2SearchCriteria titleV2SearchCriteria) throws ApiException {
		return titleApi.v2SearchTitles(titleV2SearchCriteria.getPageNumber(), titleV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(titleV2SearchCriteria.getSort()), titleV2SearchCriteria.getName(),
				titleV2SearchCriteria.getMilitaryRank(), titleV2SearchCriteria.getFleetRank(), titleV2SearchCriteria.getReligiousTitle(),
				titleV2SearchCriteria.getEducationTitle(), titleV2SearchCriteria.getMirror());
	}

}
