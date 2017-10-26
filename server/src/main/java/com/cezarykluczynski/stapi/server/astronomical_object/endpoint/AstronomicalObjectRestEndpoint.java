package com.cezarykluczynski.stapi.server.astronomical_object.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.astronomical_object.reader.AstronomicalObjectRestReader;
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
public class AstronomicalObjectRestEndpoint {

	public static final String ADDRESS = "/v1/rest/astronomicalObject";

	private final AstronomicalObjectRestReader astronomicalObjectRestReader;

	public AstronomicalObjectRestEndpoint(AstronomicalObjectRestReader astronomicalObjectRestReader) {
		this.astronomicalObjectRestReader = astronomicalObjectRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public AstronomicalObjectFullResponse getAstronomicalObject(@QueryParam("uid") String uid) {
		return astronomicalObjectRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public AstronomicalObjectBaseResponse searchAstronomicalObject(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return astronomicalObjectRestReader.readBase(AstronomicalObjectRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public AstronomicalObjectBaseResponse searchAstronomicalObject(@BeanParam AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams) {
		return astronomicalObjectRestReader.readBase(astronomicalObjectRestBeanParams);
	}

}
