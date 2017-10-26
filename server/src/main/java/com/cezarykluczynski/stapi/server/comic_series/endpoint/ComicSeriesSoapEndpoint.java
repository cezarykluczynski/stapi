package com.cezarykluczynski.stapi.server.comic_series.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;
import com.cezarykluczynski.stapi.server.comic_series.reader.ComicSeriesSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class ComicSeriesSoapEndpoint implements ComicSeriesPortType {

	public static final String ADDRESS = "/v1/soap/comicSeries";

	private final ComicSeriesSoapReader comicSeriesSoapReader;

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
