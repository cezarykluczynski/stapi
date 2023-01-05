package com.cezarykluczynski.stapi.server.comics.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFullResponse;
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams;
import com.cezarykluczynski.stapi.server.comics.reader.ComicsRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
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
public class ComicsRestEndpoint {

	public static final String ADDRESS = "/v1/rest/comics";

	private final ComicsRestReader comicsRestReader;

	public ComicsRestEndpoint(ComicsRestReader comicsRestReader) {
		this.comicsRestReader = comicsRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicsFullResponse getComics(@QueryParam("uid") String uid) {
		return comicsRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicsBaseResponse searchComics(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return comicsRestReader.readBase(ComicsRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ComicsBaseResponse searchComics(@BeanParam ComicsRestBeanParams comicsRestBeanParams) {
		return comicsRestReader.readBase(comicsRestBeanParams);
	}

}
