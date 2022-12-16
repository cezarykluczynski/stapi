package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.TechnologyApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Technology {

	private final TechnologyApi technologyApi;

	public Technology(TechnologyApi technologyApi) {
		this.technologyApi = technologyApi;
	}

	@Deprecated
	public TechnologyFullResponse get(String uid) throws ApiException {
		return technologyApi.v1RestTechnologyGet(uid, null);
	}

	public TechnologyV2FullResponse getV2(String uid) throws ApiException {
		return technologyApi.v2RestTechnologyGet(uid);
	}

	@Deprecated
	public TechnologyBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean borgTechnology,
			Boolean borgComponent, Boolean communicationsTechnology, Boolean computerTechnology, Boolean computerProgramming, Boolean subroutine,
			Boolean database, Boolean energyTechnology, Boolean fictionalTechnology, Boolean holographicTechnology, Boolean identificationTechnology,
			Boolean lifeSupportTechnology, Boolean sensorTechnology, Boolean shieldTechnology, Boolean tool, Boolean culinaryTool,
			Boolean engineeringTool, Boolean householdTool, Boolean medicalEquipment, Boolean transporterTechnology) throws ApiException {
		return technologyApi.v1RestTechnologySearchPost(pageNumber, pageSize, sort, null, name, borgTechnology, borgComponent,
				communicationsTechnology, computerTechnology, computerProgramming, subroutine, database, energyTechnology, fictionalTechnology,
				holographicTechnology, identificationTechnology, lifeSupportTechnology, sensorTechnology, shieldTechnology, tool, culinaryTool,
				engineeringTool, householdTool, medicalEquipment, transporterTechnology);
	}

	public TechnologyV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean borgTechnology,
			Boolean borgComponent, Boolean communicationsTechnology, Boolean computerTechnology, Boolean computerProgramming, Boolean subroutine,
			Boolean database, Boolean energyTechnology, Boolean fictionalTechnology, Boolean holographicTechnology, Boolean identificationTechnology,
			Boolean lifeSupportTechnology, Boolean sensorTechnology, Boolean shieldTechnology, Boolean securityTechnology,
			Boolean propulsionTechnology, Boolean spacecraftComponent, Boolean warpTechnology, Boolean transwarpTechnology,
			Boolean timeTravelTechnology, Boolean militaryTechnology, Boolean victualTechnology, Boolean tool, Boolean culinaryTool,
			Boolean engineeringTool, Boolean householdTool, Boolean medicalEquipment, Boolean transporterTechnology,
			Boolean transportationTechnology, Boolean weaponComponent, Boolean artificialLifeformComponent) throws ApiException {
		return technologyApi.v2RestTechnologySearchPost(pageNumber, pageSize, sort, name, borgTechnology, borgComponent,
				communicationsTechnology, computerTechnology, computerProgramming, subroutine, database, energyTechnology, fictionalTechnology,
				holographicTechnology, identificationTechnology, lifeSupportTechnology, sensorTechnology, shieldTechnology, securityTechnology,
				propulsionTechnology, spacecraftComponent, warpTechnology, transwarpTechnology, timeTravelTechnology, militaryTechnology,
				victualTechnology, tool, culinaryTool, engineeringTool, householdTool, medicalEquipment, transporterTechnology,
				transportationTechnology, weaponComponent, artificialLifeformComponent);
	}

}
