package com.cezarykluczynski.stapi.server.company.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.CompanyBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.CompanyFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.company.reader.CompanyRestReader;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
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
