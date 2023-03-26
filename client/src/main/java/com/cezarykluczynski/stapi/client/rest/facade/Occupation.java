package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.OccupationApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2SearchCriteria;

public class Occupation {

	private final OccupationApi occupationApi;

	public Occupation(OccupationApi occupationApi) {
		this.occupationApi = occupationApi;
	}

	public OccupationV2FullResponse getV2(String uid) throws ApiException {
		return occupationApi.v2GetOccupation(uid);
	}

	public OccupationV2BaseResponse searchV2(OccupationV2SearchCriteria occupationV2SearchCriteria) throws ApiException {
		return occupationApi.v2SearchOccupations(occupationV2SearchCriteria.getPageNumber(), occupationV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(occupationV2SearchCriteria.getSort()), occupationV2SearchCriteria.getName(),
				occupationV2SearchCriteria.getArtsOccupation(), occupationV2SearchCriteria.getCommunicationOccupation(),
				occupationV2SearchCriteria.getEconomicOccupation(), occupationV2SearchCriteria.getEducationOccupation(),
				occupationV2SearchCriteria.getEntertainmentOccupation(), occupationV2SearchCriteria.getIllegalOccupation(),
				occupationV2SearchCriteria.getLegalOccupation(), occupationV2SearchCriteria.getMedicalOccupation(),
				occupationV2SearchCriteria.getScientificOccupation(), occupationV2SearchCriteria.getSportsOccupation(),
				occupationV2SearchCriteria.getVictualOccupation());
	}

}
