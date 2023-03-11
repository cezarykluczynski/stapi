package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.TitleV2SearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.TitleApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Title {

	private final TitleApi titleApi;

	public Title(TitleApi titleApi) {
		this.titleApi = titleApi;
	}

	@Deprecated
	public TitleFullResponse get(String uid) throws ApiException {
		return titleApi.v1RestTitleGet(uid);
	}

	public TitleV2FullResponse getV2(String uid) throws ApiException {
		return titleApi.v2RestTitleGet(uid);
	}

	@Deprecated
	public TitleBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean militaryRank, Boolean fleetRank,
			Boolean religiousTitle, Boolean position, Boolean mirror) throws ApiException {
		return titleApi.v1RestTitleSearchPost(pageNumber, pageSize, sort, name, militaryRank, fleetRank, religiousTitle, position, mirror);
	}

	@Deprecated
	public TitleV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean militaryRank, Boolean fleetRank,
			Boolean religiousTitle, Boolean educationTitle, Boolean mirror) throws ApiException {
		return titleApi.v2RestTitleSearchPost(pageNumber, pageSize, sort, name, militaryRank, fleetRank, religiousTitle, educationTitle, mirror);
	}

	public TitleV2BaseResponse searchV2(TitleV2SearchCriteria titleV2SearchCriteria) throws ApiException {
		return titleApi.v2RestTitleSearchPost(titleV2SearchCriteria.getPageNumber(), titleV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(titleV2SearchCriteria.getSort()), titleV2SearchCriteria.getName(),
				titleV2SearchCriteria.getMilitaryRank(), titleV2SearchCriteria.getFleetRank(), titleV2SearchCriteria.getReligiousTitle(),
				titleV2SearchCriteria.getEducationTitle(), titleV2SearchCriteria.getMirror());
	}

}
