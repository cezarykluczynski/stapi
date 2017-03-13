package com.cezarykluczynski.stapi.server.movie.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;
import com.cezarykluczynski.stapi.client.v1.soap.MovieRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieResponse;
import com.cezarykluczynski.stapi.server.movie.reader.MovieSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class MovieSoapEndpoint implements MoviePortType {

	private MovieSoapReader seriesSoapReader;

	public MovieSoapEndpoint(MovieSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public MovieResponse getMovies(@WebParam(partName = "request", name = "MovieRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/movie") MovieRequest request) {
		return seriesSoapReader.readBase(request);
	}

}
