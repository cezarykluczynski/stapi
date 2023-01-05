package com.cezarykluczynski.stapi.server.movie.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams;
import com.cezarykluczynski.stapi.server.movie.reader.MovieRestReader;
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
