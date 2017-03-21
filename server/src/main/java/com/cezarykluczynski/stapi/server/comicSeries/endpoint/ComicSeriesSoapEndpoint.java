package com.cezarykluczynski.stapi.server.comicSeries.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;
import com.cezarykluczynski.stapi.server.comicSeries.reader.ComicSeriesSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class ComicSeriesSoapEndpoint implements ComicSeriesPortType {

	private ComicSeriesSoapReader comicSeriesSoapReader;

	public ComicSeriesSoapEndpoint(ComicSeriesSoapReader comicSeriesSoapReader) {
		this.comicSeriesSoapReader = comicSeriesSoapReader;
	}

	@Override
	public ComicSeriesBaseResponse getComicSeriesBase(@WebParam(partName = "request", name = "ComicSeriesBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comicSeries") ComicSeriesBaseRequest request) {
		return comicSeriesSoapReader.readBase(request);
	}

	@Override
	public ComicSeriesFullResponse getComicSeriesFull(@WebParam(partName = "request", name = "ComicSeriesFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/comicSeries") ComicSeriesFullRequest request) {
		return comicSeriesSoapReader.readFull(request);
	}

}
