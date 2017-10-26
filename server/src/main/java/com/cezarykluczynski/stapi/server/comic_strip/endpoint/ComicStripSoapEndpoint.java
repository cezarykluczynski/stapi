package com.cezarykluczynski.stapi.server.comic_strip.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripPortType;
import com.cezarykluczynski.stapi.server.comic_strip.reader.ComicStripSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class ComicStripSoapEndpoint implements ComicStripPortType {

	public static final String ADDRESS = "/v1/soap/comicStrip";

	private final ComicStripSoapReader comicStripSoapReader;

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
