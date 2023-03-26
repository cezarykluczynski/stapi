package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.LocationApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.LocationV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.LocationV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.LocationV2SearchCriteria;

public class Location {

	private final LocationApi locationApi;

	public Location(LocationApi locationApi) {
		this.locationApi = locationApi;
	}

	public LocationV2FullResponse getV2(String uid) throws ApiException {
		return locationApi.v2GetLocation(uid);
	}

	public LocationV2BaseResponse searchV2(LocationV2SearchCriteria locationV2SearchCriteria) throws ApiException {
		return locationApi.v2SearchLocations(locationV2SearchCriteria.getPageNumber(), locationV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(locationV2SearchCriteria.getSort()), locationV2SearchCriteria.getName(),
				locationV2SearchCriteria.getEarthlyLocation(), locationV2SearchCriteria.getQonosLocation(),
				locationV2SearchCriteria.getFictionalLocation(), locationV2SearchCriteria.getMythologicalLocation(),
				locationV2SearchCriteria.getReligiousLocation(), locationV2SearchCriteria.getGeographicalLocation(),
				locationV2SearchCriteria.getBodyOfWater(), locationV2SearchCriteria.getCountry(), locationV2SearchCriteria.getSubnationalEntity(),
				locationV2SearchCriteria.getSettlement(), locationV2SearchCriteria.getUsSettlement(), locationV2SearchCriteria.getBajoranSettlement(),
				locationV2SearchCriteria.getColony(), locationV2SearchCriteria.getLandform(), locationV2SearchCriteria.getRoad(),
				locationV2SearchCriteria.getStructure(), locationV2SearchCriteria.getShipyard(), locationV2SearchCriteria.getBuildingInterior(),
				locationV2SearchCriteria.getEstablishment(), locationV2SearchCriteria.getMedicalEstablishment(),
				locationV2SearchCriteria.getDs9Establishment(), locationV2SearchCriteria.getSchool(), locationV2SearchCriteria.getRestaurant(),
				locationV2SearchCriteria.getResidence(), locationV2SearchCriteria.getMirror(), locationV2SearchCriteria.getAlternateReality());
	}

}
