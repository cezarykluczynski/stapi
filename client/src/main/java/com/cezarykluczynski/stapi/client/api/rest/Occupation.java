package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.OccupationV2SearchCriteria;
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

	@Deprecated
	public OccupationV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean artsOccupation,
			Boolean communicationOccupation, Boolean economicOccupation, Boolean educationOccupation, Boolean entertainmentOccupation,
			Boolean illegalOccupation, Boolean legalOccupation, Boolean medicalOccupation, Boolean scientificOccupation, Boolean sportsOccupation,
			Boolean victualOccupation) throws ApiException {
		return occupationApi.v2RestOccupationSearchPost(pageNumber, pageSize, sort, name, artsOccupation, communicationOccupation,
				economicOccupation, educationOccupation, entertainmentOccupation, illegalOccupation, legalOccupation, medicalOccupation,
				scientificOccupation, sportsOccupation, victualOccupation);
	}

	public OccupationV2BaseResponse searchV2(OccupationV2SearchCriteria occupationV2SearchCriteria) throws ApiException {
		return occupationApi.v2RestOccupationSearchPost(occupationV2SearchCriteria.getPageNumber(), occupationV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(occupationV2SearchCriteria.getSort()), occupationV2SearchCriteria.getName(),
				occupationV2SearchCriteria.getArtsOccupation(), occupationV2SearchCriteria.getCommunicationOccupation(),
				occupationV2SearchCriteria.getEconomicOccupation(), occupationV2SearchCriteria.getEducationOccupation(),
				occupationV2SearchCriteria.getEntertainmentOccupation(), occupationV2SearchCriteria.getIllegalOccupation(),
				occupationV2SearchCriteria.getLegalOccupation(), occupationV2SearchCriteria.getMedicalOccupation(),
				occupationV2SearchCriteria.getScientificOccupation(), occupationV2SearchCriteria.getSportsOccupation(),
				occupationV2SearchCriteria.getVictualOccupation());
	}

}
