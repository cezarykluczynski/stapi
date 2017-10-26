package com.cezarykluczynski.stapi.server.conflict.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams;
import com.cezarykluczynski.stapi.server.conflict.reader.ConflictRestReader;
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
public class ConflictRestEndpoint {

	public static final String ADDRESS = "/v1/rest/conflict";

	private final ConflictRestReader conflictRestReader;

	public ConflictRestEndpoint(ConflictRestReader conflictRestReader) {
		this.conflictRestReader = conflictRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ConflictFullResponse getConflict(@QueryParam("uid") String uid) {
		return conflictRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public ConflictBaseResponse searchConflict(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return conflictRestReader.readBase(ConflictRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ConflictBaseResponse searchConflict(@BeanParam ConflictRestBeanParams conflictRestBeanParams) {
		return conflictRestReader.readBase(conflictRestBeanParams);
	}

}
