package com.cezarykluczynski.stapi.server.company.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.company.dto.CompanyV2RestBeanParams;
import com.cezarykluczynski.stapi.server.company.reader.CompanyV2RestReader;
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
public class CompanyV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/company";

	private final CompanyV2RestReader companyV2RestReader;

	public CompanyV2RestEndpoint(CompanyV2RestReader companyV2RestReader) {
		this.companyV2RestReader = companyV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public CompanyV2FullResponse getCompany(@QueryParam("uid") String uid) {
		return companyV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public CompanyV2BaseResponse searchCompany(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return companyV2RestReader.readBase(CompanyV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CompanyV2BaseResponse searchCompany(@BeanParam CompanyV2RestBeanParams companyRestBeanParams) {
		return companyV2RestReader.readBase(companyRestBeanParams);
	}

}
