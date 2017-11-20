package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;

public class Series {

	private final SeriesPortType seriesPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Series(SeriesPortType seriesPortType, ApiKeySupplier apiKeySupplier) {
		this.seriesPortType = seriesPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public SeriesFullResponse get(SeriesFullRequest request) {
		apiKeySupplier.supply(request);
		return seriesPortType.getSeriesFull(request);
	}

	public SeriesBaseResponse search(SeriesBaseRequest request) {
		apiKeySupplier.supply(request);
		return seriesPortType.getSeriesBase(request);
	}

}
