package com.cezarykluczynski.stapi.server.title.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.title.dto.TitleV2RestBeanParams;
import com.cezarykluczynski.stapi.server.title.reader.TitleV2RestReader;
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
public class TitleV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/title";

	private final TitleV2RestReader titleV2RestReader;

	public TitleV2RestEndpoint(TitleV2RestReader titleV2RestReader) {
		this.titleV2RestReader = titleV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public TitleV2FullResponse getTitle(@QueryParam("uid") String uid) {
		return titleV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public TitleV2BaseResponse searchTitle(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return titleV2RestReader.readBase(TitleV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public TitleV2BaseResponse searchTitle(@BeanParam TitleV2RestBeanParams titleRestBeanParams) {
		return titleV2RestReader.readBase(titleRestBeanParams);
	}

}
