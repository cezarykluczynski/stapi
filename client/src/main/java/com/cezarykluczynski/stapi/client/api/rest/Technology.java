package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.TechnologyApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFullResponse;

@SuppressWarnings("ParameterNumber")
public class Technology {

	private final TechnologyApi technologyApi;

	private final String apiKey;

	public Technology(TechnologyApi technologyApi, String apiKey) {
		this.technologyApi = technologyApi;
		this.apiKey = apiKey;
	}

	public TechnologyFullResponse get(String uid) throws ApiException {
		return technologyApi.technologyGet(uid, apiKey);
	}

	public TechnologyBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean borgTechnology,
			Boolean borgComponent, Boolean communicationsTechnology, Boolean computerTechnology, Boolean computerProgramming, Boolean subroutine,
			Boolean database, Boolean energyTechnology, Boolean fictionalTechnology, Boolean holographicTechnology, Boolean identificationTechnology,
			Boolean lifeSupportTechnology, Boolean sensorTechnology, Boolean shieldTechnology, Boolean tool, Boolean culinaryTool,
			Boolean engineeringTool, Boolean householdTool, Boolean medicalEquipment, Boolean transporterTechnology) throws ApiException {
		return technologyApi.technologySearchPost(pageNumber, pageSize, sort, apiKey, name, borgTechnology, borgComponent, communicationsTechnology,
				computerTechnology, computerProgramming, subroutine, database, energyTechnology, fictionalTechnology, holographicTechnology,
				identificationTechnology, lifeSupportTechnology, sensorTechnology, shieldTechnology, tool, culinaryTool, engineeringTool,
				householdTool, medicalEquipment, transporterTechnology);
	}

}
