package com.cezarykluczynski.stapi.server.company.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.company.reader.CompanyRestReader;
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
public class CompanyRestEndpoint {

	public static final String ADDRESS = "/v1/rest/company";

	private final CompanyRestReader companyRestReader;

	public CompanyRestEndpoint(CompanyRestReader companyRestReader) {
		this.companyRestReader = companyRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public CompanyFullResponse getCompany(@QueryParam("uid") String uid) {
		return companyRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public CompanyBaseResponse searchCompanies(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return companyRestReader.readBase(CompanyRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CompanyBaseResponse searchCompanies(@BeanParam CompanyRestBeanParams companyRestBeanParams) {
		return companyRestReader.readBase(companyRestBeanParams);
	}

}
