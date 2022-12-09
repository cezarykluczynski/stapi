package com.cezarykluczynski.stapi.server.conflict.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams;
import com.cezarykluczynski.stapi.server.conflict.reader.ConflictV2RestReader;
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
public class ConflictV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/conflict";

	private final ConflictV2RestReader conflictV2RestReader;

	public ConflictV2RestEndpoint(ConflictV2RestReader conflictV2RestReader) {
		this.conflictV2RestReader = conflictV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ConflictV2FullResponse getConflict(@QueryParam("uid") String uid) {
		return conflictV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public ConflictV2BaseResponse searchConflict(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return conflictV2RestReader.readBase(ConflictRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ConflictV2BaseResponse searchConflict(@BeanParam ConflictRestBeanParams conflictRestBeanParams) {
		return conflictV2RestReader.readBase(conflictRestBeanParams);
	}

}
