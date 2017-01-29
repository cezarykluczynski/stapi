package com.cezarykluczynski.stapi.server.company.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.company.reader.CompanyRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
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
	public CompanyResponse getCompanys(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return companyRestReader.read(CompanyRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CompanyResponse searchCompanys(@BeanParam CompanyRestBeanParams seriesRestBeanParams) {
		return companyRestReader.read(seriesRestBeanParams);
	}

}
