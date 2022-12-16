package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.OccupationApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Occupation {

	private final OccupationApi occupationApi;

	public Occupation(OccupationApi occupationApi) {
		this.occupationApi = occupationApi;
	}

	@Deprecated
	public OccupationFullResponse get(String uid) throws ApiException {
		return occupationApi.v1RestOccupationGet(uid, null);
	}

	public OccupationV2FullResponse getV2(String uid) throws ApiException {
		return occupationApi.v2RestOccupationGet(uid);
	}

	@Deprecated
	public OccupationBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean legalOccupation,
			Boolean medicalOccupation, Boolean scientificOccupation) throws ApiException {
		return occupationApi.v1RestOccupationSearchPost(pageNumber, pageSize, sort, null, name, legalOccupation, medicalOccupation,
				scientificOccupation);
	}

	public OccupationV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean artsOccupation,
			Boolean communicationOccupation, Boolean economicOccupation, Boolean educationOccupation, Boolean entertainmentOccupation,
			Boolean illegalOccupation, Boolean legalOccupation, Boolean medicalOccupation, Boolean scientificOccupation, Boolean sportsOccupation,
			Boolean victualOccupation) throws ApiException {
		return occupationApi.v2RestOccupationSearchPost(pageNumber, pageSize, sort, name, artsOccupation, communicationOccupation,
				economicOccupation, educationOccupation, entertainmentOccupation, illegalOccupation, legalOccupation, medicalOccupation,
				scientificOccupation, sportsOccupation, victualOccupation);
	}

}
