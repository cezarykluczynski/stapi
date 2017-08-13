package com.cezarykluczynski.stapi.server.location.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.location.dto.LocationRestBeanParams;
import com.cezarykluczynski.stapi.server.location.reader.LocationRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Service
@Produces(MediaType.APPLICATION_JSON)
public class LocationRestEndpoint {

	public static final String ADDRESS = "/v1/rest/location";

	private final LocationRestReader locationRestReader;

	@Inject
	public LocationRestEndpoint(LocationRestReader locationRestReader) {
		this.locationRestReader = locationRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public LocationFullResponse getLocation(@QueryParam("uid") String uid) {
		return locationRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public LocationBaseResponse searchLocations(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return locationRestReader.readBase(LocationRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public LocationBaseResponse searchLocations(@BeanParam LocationRestBeanParams locationRestBeanParams) {
		return locationRestReader.readBase(locationRestBeanParams);
	}

}
