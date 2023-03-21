package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.TitleV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.TitleApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TitleFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.TitleV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TitleV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Title {

	private final TitleApi titleApi;

	public Title(TitleApi titleApi) {
		this.titleApi = titleApi;
	}

	@Deprecated
	public TitleFullResponse get(String uid) throws ApiException {
		return titleApi.v1Get(uid);
	}

	public TitleV2FullResponse getV2(String uid) throws ApiException {
		return titleApi.v2Get(uid);
	}

	@Deprecated
	public TitleBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean militaryRank, Boolean fleetRank,
			Boolean religiousTitle, Boolean position, Boolean mirror) throws ApiException {
		return titleApi.v1Search(pageNumber, pageSize, sort, name, militaryRank, fleetRank, religiousTitle, position, mirror);
	}

	@Deprecated
	public TitleV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean militaryRank, Boolean fleetRank,
			Boolean religiousTitle, Boolean educationTitle, Boolean mirror) throws ApiException {
		return titleApi.v2Search(pageNumber, pageSize, sort, name, militaryRank, fleetRank, religiousTitle, educationTitle, mirror);
	}

	public TitleV2BaseResponse searchV2(TitleV2SearchCriteria titleV2SearchCriteria) throws ApiException {
		return titleApi.v2Search(titleV2SearchCriteria.getPageNumber(), titleV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(titleV2SearchCriteria.getSort()), titleV2SearchCriteria.getName(),
				titleV2SearchCriteria.getMilitaryRank(), titleV2SearchCriteria.getFleetRank(), titleV2SearchCriteria.getReligiousTitle(),
				titleV2SearchCriteria.getEducationTitle(), titleV2SearchCriteria.getMirror());
	}

}
