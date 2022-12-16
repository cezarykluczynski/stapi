package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;

public class Movie {

	private final MoviePortType moviePortType;

	public Movie(MoviePortType moviePortType) {
		this.moviePortType = moviePortType;
	}

	@Deprecated
	public MovieFullResponse get(MovieFullRequest request) {
		return moviePortType.getMovieFull(request);
	}

	@Deprecated
	public MovieBaseResponse search(MovieBaseRequest request) {
		return moviePortType.getMovieBase(request);
	}

}
