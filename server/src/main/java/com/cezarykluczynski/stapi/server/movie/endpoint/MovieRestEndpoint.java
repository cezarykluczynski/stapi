package com.cezarykluczynski.stapi.server.movie.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams;
import com.cezarykluczynski.stapi.server.movie.reader.MovieRestReader;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("v1/rest/movie")
@Produces(MediaType.APPLICATION_JSON)
public class MovieRestEndpoint {

	private MovieRestReader movieRestReader;

	@Inject
	public MovieRestEndpoint(MovieRestReader movieRestReader) {
		this.movieRestReader = movieRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public MovieFullResponse getMovie(@QueryParam("guid") String guid) {
		return movieRestReader.readFull(guid);
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
