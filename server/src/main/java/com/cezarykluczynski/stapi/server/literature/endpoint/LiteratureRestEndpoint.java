package com.cezarykluczynski.stapi.server.literature.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.literature.dto.LiteratureRestBeanParams;
import com.cezarykluczynski.stapi.server.literature.reader.LiteratureRestReader;
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
public class LiteratureRestEndpoint {

	public static final String ADDRESS = "/v1/rest/literature";

	private final LiteratureRestReader literatureRestReader;

	public LiteratureRestEndpoint(LiteratureRestReader literatureRestReader) {
		this.literatureRestReader = literatureRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public LiteratureFullResponse getLiterature(@QueryParam("uid") String uid) {
		return literatureRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public LiteratureBaseResponse searchLiterature(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return literatureRestReader.readBase(LiteratureRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public LiteratureBaseResponse searchLiterature(@BeanParam LiteratureRestBeanParams literatureRestBeanParams) {
		return literatureRestReader.readBase(literatureRestBeanParams);
	}

}
