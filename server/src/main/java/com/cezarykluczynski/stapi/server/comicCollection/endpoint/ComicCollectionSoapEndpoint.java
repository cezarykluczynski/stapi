package com.cezarykluczynski.stapi.server.comicCollection.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionResponse;
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class ComicCollectionSoapEndpoint implements ComicCollectionPortType {

	private ComicCollectionSoapReader seriesSoapReader;

	public ComicCollectionSoapEndpoint(ComicCollectionSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	public ComicCollectionResponse getComicCollections(@WebParam(partName = "request", name = "ComicCollectionRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comicCollection") ComicCollectionRequest request) {
		return seriesSoapReader.read(request);
	}

}
