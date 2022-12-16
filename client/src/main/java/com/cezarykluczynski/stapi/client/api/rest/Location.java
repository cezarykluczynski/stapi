package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.LocationApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Location {

	private final LocationApi locationApi;

	public Location(LocationApi locationApi) {
		this.locationApi = locationApi;
	}

	@Deprecated
	public LocationFullResponse get(String uid) throws ApiException {
		return locationApi.v1RestLocationGet(uid, null);
	}

	public LocationV2FullResponse getV2(String uid) throws ApiException {
		return locationApi.v2RestLocationGet(uid);
	}

	@Deprecated
	public LocationBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean earthlyLocation,
			Boolean fictionalLocation, Boolean religiousLocation, Boolean geographicalLocation, Boolean bodyOfWater, Boolean country,
			Boolean subnationalEntity, Boolean settlement, Boolean usSettlement, Boolean bajoranSettlement, Boolean colony, Boolean landform,
			Boolean landmark, Boolean road, Boolean structure, Boolean shipyard, Boolean buildingInterior, Boolean establishment,
			Boolean medicalEstablishment, Boolean ds9Establishment, Boolean school, Boolean mirror, Boolean alternateReality) throws ApiException {
		return locationApi.v1RestLocationSearchPost(pageNumber, pageSize, sort, null, name, earthlyLocation, fictionalLocation, religiousLocation,
				geographicalLocation, bodyOfWater, country, subnationalEntity, settlement, usSettlement, bajoranSettlement, colony, landform,
				landmark, road, structure, shipyard, buildingInterior, establishment, medicalEstablishment, ds9Establishment, school, mirror,
				alternateReality);
	}

	public LocationV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean earthlyLocation,
			Boolean qonosLocation, Boolean fictionalLocation, Boolean mythologicalLocation, Boolean religiousLocation, Boolean geographicalLocation,
			Boolean bodyOfWater, Boolean country, Boolean subnationalEntity, Boolean settlement, Boolean usSettlement, Boolean bajoranSettlement,
			Boolean colony, Boolean landform, Boolean road, Boolean structure, Boolean shipyard, Boolean buildingInterior, Boolean establishment,
			Boolean medicalEstablishment, Boolean ds9Establishment, Boolean school, Boolean restaurant, Boolean residence, Boolean mirror,
			Boolean alternateReality) throws ApiException {
		return locationApi.v2RestLocationSearchPost(pageNumber, pageSize, sort, name, earthlyLocation, qonosLocation, fictionalLocation,
				mythologicalLocation, religiousLocation, geographicalLocation, bodyOfWater, country, subnationalEntity, settlement, usSettlement,
				bajoranSettlement, colony, landform, road, structure, shipyard, buildingInterior, establishment, medicalEstablishment,
				ds9Establishment, school, restaurant, residence, mirror, alternateReality);
	}

}
