package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LocationPortType;

public class Location {

	private final LocationPortType locationPortType;

	public Location(LocationPortType locationPortType) {
		this.locationPortType = locationPortType;
	}

	@Deprecated
	public LocationFullResponse get(LocationFullRequest request) {
		return locationPortType.getLocationFull(request);
	}

	@Deprecated
	public LocationBaseResponse search(LocationBaseRequest request) {
		return locationPortType.getLocationBase(request);
	}

}
