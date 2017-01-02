package com.cezarykluczynski.stapi.server.movie.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams;
import com.cezarykluczynski.stapi.server.movie.reader.MovieRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
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
	public MovieResponse getMovies(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return movieRestReader.read(MovieRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MovieResponse searchMovies(@BeanParam MovieRestBeanParams seriesRestBeanParams) {
		return movieRestReader.read(seriesRestBeanParams);
	}

}
