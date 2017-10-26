package com.cezarykluczynski.stapi.server.movie.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams;
import com.cezarykluczynski.stapi.server.movie.reader.MovieRestReader;
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
public class MovieRestEndpoint {

	public static final String ADDRESS = "/v1/rest/movie";

	private final MovieRestReader movieRestReader;

	public MovieRestEndpoint(MovieRestReader movieRestReader) {
		this.movieRestReader = movieRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public MovieFullResponse getMovie(@QueryParam("uid") String uid) {
		return movieRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public MovieBaseResponse searchMovie(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return movieRestReader.readBase(MovieRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MovieBaseResponse searchMovie(@BeanParam MovieRestBeanParams movieRestBeanParams) {
		return movieRestReader.readBase(movieRestBeanParams);
	}

}
