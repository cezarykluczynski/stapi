package com.cezarykluczynski.stapi.server.comicCollection.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionPortType;
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class ComicCollectionSoapEndpoint implements ComicCollectionPortType {

	private ComicCollectionSoapReader comicCollectionSoapReader;

	public ComicCollectionSoapEndpoint(ComicCollectionSoapReader comicCollectionSoapReader) {
		this.comicCollectionSoapReader = comicCollectionSoapReader;
	}

	@Override
	public ComicCollectionBaseResponse getComicCollectionBase(@WebParam(partName = "request", name = "ComicCollectionBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comicCollection") ComicCollectionBaseRequest request) {
		return comicCollectionSoapReader.readBase(request);
	}

	@Override
	public ComicCollectionFullResponse getComicCollectionFull(@WebParam(partName = "request", name = "ComicCollectionFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comicCollection") ComicCollectionFullRequest request) {
		return comicCollectionSoapReader.readFull(request);
	}

}
