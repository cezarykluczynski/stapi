package com.cezarykluczynski.stapi.server.comics.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsPortType;
import com.cezarykluczynski.stapi.server.comics.reader.ComicsSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class ComicsSoapEndpoint implements ComicsPortType {

	private ComicsSoapReader comicsSoapReader;

	public ComicsSoapEndpoint(ComicsSoapReader comicsSoapReader) {
		this.comicsSoapReader = comicsSoapReader;
	}

	@Override
	public ComicsBaseResponse getComicsBase(@WebParam(partName = "request", name = "ComicsBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comics") ComicsBaseRequest request) {
		return comicsSoapReader.readBase(request);
	}

	@Override
	public ComicsFullResponse getComicsFull(@WebParam(partName = "request", name = "ComicsFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comics") ComicsFullRequest request) {
		return comicsSoapReader.readFull(request);
	}

}
