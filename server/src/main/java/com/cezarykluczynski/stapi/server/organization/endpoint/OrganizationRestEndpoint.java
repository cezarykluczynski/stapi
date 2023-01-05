package com.cezarykluczynski.stapi.server.organization.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.organization.dto.OrganizationRestBeanParams;
import com.cezarykluczynski.stapi.server.organization.reader.OrganizationRestReader;
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
public class OrganizationRestEndpoint {

	public static final String ADDRESS = "/v1/rest/organization";

	private final OrganizationRestReader organizationRestReader;

	public OrganizationRestEndpoint(OrganizationRestReader organizationRestReader) {
		this.organizationRestReader = organizationRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public OrganizationFullResponse getOrganization(@QueryParam("uid") String uid) {
		return organizationRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public OrganizationBaseResponse searchOrganizations(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return organizationRestReader.readBase(OrganizationRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public OrganizationBaseResponse searchOrganizations(@BeanParam OrganizationRestBeanParams organizationRestBeanParams) {
		return organizationRestReader.readBase(organizationRestBeanParams);
	}

}
