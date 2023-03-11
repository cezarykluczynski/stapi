package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.LocationV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.LocationApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.LocationBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.LocationFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.LocationV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.LocationV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Location {

	private final LocationApi locationApi;

	public Location(LocationApi locationApi) {
		this.locationApi = locationApi;
	}

	@Deprecated
	public LocationFullResponse get(String uid) throws ApiException {
		return locationApi.v1RestLocationGet(uid);
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
		return locationApi.v1RestLocationSearchPost(pageNumber, pageSize, sort, name, earthlyLocation, fictionalLocation, religiousLocation,
				geographicalLocation, bodyOfWater, country, subnationalEntity, settlement, usSettlement, bajoranSettlement, colony, landform,
				landmark, road, structure, shipyard, buildingInterior, establishment, medicalEstablishment, ds9Establishment, school, mirror,
				alternateReality);
	}

	@Deprecated
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

	public LocationV2BaseResponse searchV2(LocationV2SearchCriteria locationV2SearchCriteria) throws ApiException {
		return locationApi.v2RestLocationSearchPost(locationV2SearchCriteria.getPageNumber(), locationV2SearchCriteria.getPageSize(),
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
