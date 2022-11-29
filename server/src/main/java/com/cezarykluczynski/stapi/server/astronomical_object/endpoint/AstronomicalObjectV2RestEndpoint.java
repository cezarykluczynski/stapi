package com.cezarykluczynski.stapi.server.astronomical_object.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectV2FullResponse;
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.astronomical_object.reader.AstronomicalObjectV2RestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
		return astronomicalObjectV2RestReader.readBase(AstronomicalObjectRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public AstronomicalObjectV2BaseResponse searchAstronomicalObject(@BeanParam AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams) {
		return astronomicalObjectV2RestReader.readBase(astronomicalObjectRestBeanParams);
	}

}
