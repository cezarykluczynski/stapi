package com.cezarykluczynski.stapi.server.comics.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsResponse;
import com.cezarykluczynski.stapi.server.comics.reader.ComicsSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class ComicsSoapEndpoint implements ComicsPortType {

	private ComicsSoapReader seriesSoapReader;

	public ComicsSoapEndpoint(ComicsSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	public ComicsResponse getComics(@WebParam(partName = "request", name = "ComicsRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comics") ComicsRequest request) {
		return seriesSoapReader.readBase(request);
	}

}
