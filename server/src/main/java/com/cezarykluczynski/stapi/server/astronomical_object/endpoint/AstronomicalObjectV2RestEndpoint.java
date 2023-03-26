package com.cezarykluczynski.stapi.server.astronomical_object.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2FullResponse;
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectV2RestBeanParams;
import com.cezarykluczynski.stapi.server.astronomical_object.reader.AstronomicalObjectV2RestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
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
public class AstronomicalObjectV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/astronomicalObject";

	private final AstronomicalObjectV2RestReader astronomicalObjectV2RestReader;

	public AstronomicalObjectV2RestEndpoint(AstronomicalObjectV2RestReader astronomicalObjectV2RestReader) {
		this.astronomicalObjectV2RestReader = astronomicalObjectV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public AstronomicalObjectV2FullResponse getAstronomicalObject(@QueryParam("uid") String uid) {
		return astronomicalObjectV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public AstronomicalObjectV2BaseResponse searchAstronomicalObject(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return astronomicalObjectV2RestReader.readBase(AstronomicalObjectV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public AstronomicalObjectV2BaseResponse searchAstronomicalObject(
			@BeanParam AstronomicalObjectV2RestBeanParams astronomicalObjectV2RestBeanParams) {
		return astronomicalObjectV2RestReader.readBase(astronomicalObjectV2RestBeanParams);
	}

}
