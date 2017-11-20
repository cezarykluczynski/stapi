package com.cezarykluczynski.stapi.client.api.soap;


import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LocationPortType;

public class Location {

	private final LocationPortType locationPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Location(LocationPortType locationPortType, ApiKeySupplier apiKeySupplier) {
		this.locationPortType = locationPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public LocationFullResponse get(LocationFullRequest request) {
		apiKeySupplier.supply(request);
		return locationPortType.getLocationFull(request);
	}

	public LocationBaseResponse search(LocationBaseRequest request) {
		apiKeySupplier.supply(request);
		return locationPortType.getLocationBase(request);
	}

}
