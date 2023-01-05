package com.cezarykluczynski.stapi.server.location.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.location.dto.LocationV2RestBeanParams;
import com.cezarykluczynski.stapi.server.location.reader.LocationV2RestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class LocationV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/location";

	private final LocationV2RestReader locationV2RestReader;

	public LocationV2RestEndpoint(LocationV2RestReader locationV2RestReader) {
		this.locationV2RestReader = locationV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public LocationV2FullResponse getLocation(@QueryParam("uid") String uid) {
		return locationV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public LocationV2BaseResponse searchLocations(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return locationV2RestReader.readBase(LocationV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public LocationV2BaseResponse searchLocations(@BeanParam LocationV2RestBeanParams locationV2RestBeanParams) {
		return locationV2RestReader.readBase(locationV2RestBeanParams);
	}

}
