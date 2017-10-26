package com.cezarykluczynski.stapi.server.magazine_series.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesPortType;
import com.cezarykluczynski.stapi.server.magazine_series.reader.MagazineSeriesSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class MagazineSeriesSoapEndpoint implements MagazineSeriesPortType {

	public static final String ADDRESS = "/v1/soap/magazineSeries";

	private final MagazineSeriesSoapReader magazineSeriesSoapReader;

	public MagazineSeriesSoapEndpoint(MagazineSeriesSoapReader magazineSeriesSoapReader) {
		this.magazineSeriesSoapReader = magazineSeriesSoapReader;
	}

	@Override
	public MagazineSeriesBaseResponse getMagazineSeriesBase(@WebParam(partName = "request", name = "MagazineSeriesBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/magazineSeries") MagazineSeriesBaseRequest request) {
		return magazineSeriesSoapReader.readBase(request);
	}

	@Override
	public MagazineSeriesFullResponse getMagazineSeriesFull(@WebParam(partName = "request", name = "MagazineSeriesFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/magazineSeries") MagazineSeriesFullRequest request) {
		return magazineSeriesSoapReader.readFull(request);
	}

}
