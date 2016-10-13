package com.cezarykluczynski.stapi.server.series.endpoint;

import com.cezarykluczynski.stapi.client.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.soap.SeriesRequest;
import com.cezarykluczynski.stapi.client.soap.SeriesResponse;
import com.cezarykluczynski.stapi.server.series.reader.SeriesSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class SeriesSoapEndpoint implements SeriesPortType {

	private SeriesSoapReader seriesSoapReader;

	public SeriesSoapEndpoint(SeriesSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public SeriesResponse getSeries(@WebParam(partName = "request", name = "SeriesRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/series") SeriesRequest request) {
		return seriesSoapReader.read(request);
	}
}
