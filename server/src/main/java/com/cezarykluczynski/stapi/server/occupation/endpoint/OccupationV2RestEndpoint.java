package com.cezarykluczynski.stapi.server.occupation.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationV2RestBeanParams;
import com.cezarykluczynski.stapi.server.occupation.reader.OccupationV2RestReader;
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
public class OccupationV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/occupation";

	private final OccupationV2RestReader occupationV2RestReader;

	public OccupationV2RestEndpoint(OccupationV2RestReader occupationV2RestReader) {
		this.occupationV2RestReader = occupationV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public OccupationV2FullResponse getOccupation(@QueryParam("uid") String uid) {
		return occupationV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public OccupationV2BaseResponse searchOccupation(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return occupationV2RestReader.readBase(OccupationV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public OccupationV2BaseResponse searchOccupation(@BeanParam OccupationV2RestBeanParams occupationRestBeanParams) {
		return occupationV2RestReader.readBase(occupationRestBeanParams);
	}

}
