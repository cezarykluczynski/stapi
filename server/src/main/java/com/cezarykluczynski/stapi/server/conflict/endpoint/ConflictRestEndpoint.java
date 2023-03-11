package com.cezarykluczynski.stapi.server.conflict.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ConflictFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams;
import com.cezarykluczynski.stapi.server.conflict.reader.ConflictRestReader;
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
