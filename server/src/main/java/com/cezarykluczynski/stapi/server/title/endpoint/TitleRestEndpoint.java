package com.cezarykluczynski.stapi.server.title.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.title.dto.TitleRestBeanParams;
import com.cezarykluczynski.stapi.server.title.reader.TitleRestReader;
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
public class TitleRestEndpoint {

	public static final String ADDRESS = "/v1/rest/title";

	private final TitleRestReader titleRestReader;

	public TitleRestEndpoint(TitleRestReader titleRestReader) {
		this.titleRestReader = titleRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public TitleFullResponse getTitle(@QueryParam("uid") String uid) {
		return titleRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public TitleBaseResponse searchTitles(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return titleRestReader.readBase(TitleRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public TitleBaseResponse searchTitles(@BeanParam TitleRestBeanParams titleRestBeanParams) {
		return titleRestReader.readBase(titleRestBeanParams);
	}

}
