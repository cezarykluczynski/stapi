package com.cezarykluczynski.stapi.server.comic_collection.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionPortType;
import com.cezarykluczynski.stapi.server.comic_collection.reader.ComicCollectionSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class ComicCollectionSoapEndpoint implements ComicCollectionPortType {

	public static final String ADDRESS = "/v1/soap/comicCollection";

	private final ComicCollectionSoapReader comicCollectionSoapReader;

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
