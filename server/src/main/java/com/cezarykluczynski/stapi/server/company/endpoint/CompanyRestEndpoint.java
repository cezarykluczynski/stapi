package com.cezarykluczynski.stapi.server.company.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.company.reader.CompanyRestReader;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("v1/rest/company")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyRestEndpoint {

	private CompanyRestReader companyRestReader;

	@Inject
	public CompanyRestEndpoint(CompanyRestReader companyRestReader) {
		this.companyRestReader = companyRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public CompanyResponse getCompanies(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return companyRestReader.readBase(CompanyRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CompanyResponse searchCompanies(@BeanParam CompanyRestBeanParams seriesRestBeanParams) {
		return companyRestReader.readBase(seriesRestBeanParams);
	}

}
