package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;

public class Movie {

	private final MoviePortType moviePortType;

	private final ApiKeySupplier apiKeySupplier;

	public Movie(MoviePortType moviePortType, ApiKeySupplier apiKeySupplier) {
		this.moviePortType = moviePortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public MovieFullResponse get(MovieFullRequest request) {
		apiKeySupplier.supply(request);
		return moviePortType.getMovieFull(request);
	}

	public MovieBaseResponse search(MovieBaseRequest request) {
		apiKeySupplier.supply(request);
		return moviePortType.getMovieBase(request);
	}

}
