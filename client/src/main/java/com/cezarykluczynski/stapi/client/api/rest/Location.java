package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.LocationApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFullResponse;

@SuppressWarnings("ParameterNumber")
public class Location {

	private final LocationApi locationApi;

	private final String apiKey;

	public Location(LocationApi locationApi, String apiKey) {
		this.locationApi = locationApi;
		this.apiKey = apiKey;
	}

	public LocationFullResponse get(String uid) throws ApiException {
		return locationApi.locationGet(uid, apiKey);
	}

	public LocationBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean earthlyLocation,
			Boolean fictionalLocation, Boolean religiousLocation, Boolean geographicalLocation, Boolean bodyOfWater, Boolean country,
			Boolean subnationalEntity, Boolean settlement, Boolean usSettlement, Boolean bajoranSettlement, Boolean colony, Boolean landform,
			Boolean landmark, Boolean road, Boolean structure, Boolean shipyard, Boolean buildingInterior, Boolean establishment,
			Boolean medicalEstablishment, Boolean ds9Establishment, Boolean school, Boolean mirror, Boolean alternateReality) throws ApiException {
		return locationApi.locationSearchPost(pageNumber, pageSize, sort, apiKey, name, earthlyLocation, fictionalLocation, religiousLocation,
				geographicalLocation, bodyOfWater, country, subnationalEntity, settlement, usSettlement, bajoranSettlement, colony, landform,
				landmark, road, structure, shipyard, buildingInterior, establishment, medicalEstablishment, ds9Establishment, school, mirror,
				alternateReality);
	}

}
