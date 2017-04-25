package com.cezarykluczynski.stapi.server.organization.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.organization.dto.OrganizationRestBeanParams;
import com.cezarykluczynski.stapi.server.organization.reader.OrganizationRestReader;
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
@Path("v1/rest/organization")
@Produces(MediaType.APPLICATION_JSON)
public class OrganizationRestEndpoint {

	private OrganizationRestReader organizationRestReader;

	@Inject
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
	public OrganizationBaseResponse searchCompanies(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return organizationRestReader.readBase(OrganizationRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public OrganizationBaseResponse searchCompanies(@BeanParam OrganizationRestBeanParams seriesRestBeanParams) {
		return organizationRestReader.readBase(seriesRestBeanParams);
	}

}
