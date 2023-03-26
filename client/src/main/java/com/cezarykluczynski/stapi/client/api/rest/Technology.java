package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.TechnologyApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2SearchCriteria;

public class Technology {

	private final TechnologyApi technologyApi;

	public Technology(TechnologyApi technologyApi) {
		this.technologyApi = technologyApi;
	}

	public TechnologyV2FullResponse getV2(String uid) throws ApiException {
		return technologyApi.v2GetTechnology(uid);
	}

	public TechnologyV2BaseResponse searchV2(TechnologyV2SearchCriteria technologyV2SearchCriteria) throws ApiException {
		return technologyApi.v2SearchTechnology(technologyV2SearchCriteria.getPageNumber(), technologyV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(technologyV2SearchCriteria.getSort()), technologyV2SearchCriteria.getName(),
				technologyV2SearchCriteria.getBorgTechnology(), technologyV2SearchCriteria.getBorgComponent(),
				technologyV2SearchCriteria.getCommunicationsTechnology(), technologyV2SearchCriteria.getComputerTechnology(),
				technologyV2SearchCriteria.getComputerProgramming(), technologyV2SearchCriteria.getSubroutine(),
				technologyV2SearchCriteria.getDatabase(), technologyV2SearchCriteria.getEnergyTechnology(),
				technologyV2SearchCriteria.getFictionalTechnology(), technologyV2SearchCriteria.getHolographicTechnology(),
				technologyV2SearchCriteria.getIdentificationTechnology(), technologyV2SearchCriteria.getLifeSupportTechnology(),
				technologyV2SearchCriteria.getSensorTechnology(), technologyV2SearchCriteria.getShieldTechnology(),
				technologyV2SearchCriteria.getSecurityTechnology(), technologyV2SearchCriteria.getPropulsionTechnology(),
				technologyV2SearchCriteria.getSpacecraftComponent(), technologyV2SearchCriteria.getWarpTechnology(),
				technologyV2SearchCriteria.getTranswarpTechnology(), technologyV2SearchCriteria.getTimeTravelTechnology(),
				technologyV2SearchCriteria.getMilitaryTechnology(), technologyV2SearchCriteria.getVictualTechnology(),
				technologyV2SearchCriteria.getTool(), technologyV2SearchCriteria.getCulinaryTool(), technologyV2SearchCriteria.getEngineeringTool(),
				technologyV2SearchCriteria.getHouseholdTool(), technologyV2SearchCriteria.getMedicalEquipment(),
				technologyV2SearchCriteria.getTransporterTechnology(), technologyV2SearchCriteria.getTransportationTechnology(),
				technologyV2SearchCriteria.getWeaponComponent(), technologyV2SearchCriteria.getArtificialLifeformComponent());
	}

}
