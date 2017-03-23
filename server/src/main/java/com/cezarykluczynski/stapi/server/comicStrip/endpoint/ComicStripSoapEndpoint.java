package com.cezarykluczynski.stapi.server.comicStrip.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripPortType;
import com.cezarykluczynski.stapi.server.comicStrip.reader.ComicStripSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class ComicStripSoapEndpoint implements ComicStripPortType {

	private ComicStripSoapReader comicStripSoapReader;

	public ComicStripSoapEndpoint(ComicStripSoapReader comicStripSoapReader) {
		this.comicStripSoapReader = comicStripSoapReader;
	}

	@Override
	public ComicStripBaseResponse getComicStripBase(@WebParam(partName = "request", name = "ComicStripBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comicStrip") ComicStripBaseRequest request) {
		return comicStripSoapReader.readBase(request);
	}

	@Override
	public ComicStripFullResponse getComicStripFull(@WebParam(partName = "request", name = "ComicStripFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comicStrip") ComicStripFullRequest request) {
		return comicStripSoapReader.readFull(request);
	}

}
