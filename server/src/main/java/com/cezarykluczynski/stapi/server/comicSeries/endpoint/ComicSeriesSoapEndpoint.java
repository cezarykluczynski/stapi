package com.cezarykluczynski.stapi.server.comicSeries.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesResponse;
import com.cezarykluczynski.stapi.server.comicSeries.reader.ComicSeriesSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class ComicSeriesSoapEndpoint implements ComicSeriesPortType {

	private ComicSeriesSoapReader seriesSoapReader;

	public ComicSeriesSoapEndpoint(ComicSeriesSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public ComicSeriesResponse getComicSeries(@WebParam(partName = "request", name = "ComicSeriesRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comicSeries") ComicSeriesRequest request) {
		return seriesSoapReader.read(request);
	}

}
