package com.cezarykluczynski.stapi.server.comicStrip.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripResponse;
import com.cezarykluczynski.stapi.server.comicStrip.reader.ComicStripSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class ComicStripSoapEndpoint implements ComicStripPortType {

	private ComicStripSoapReader seriesSoapReader;

	public ComicStripSoapEndpoint(ComicStripSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	public ComicStripResponse getComicStrip(@WebParam(partName = "request", name = "ComicStripRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comicStrip") ComicStripRequest request) {
		return seriesSoapReader.read(request);
	}

}
