package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;

public class Series {

	private final SeriesPortType seriesPortType;

	public Series(SeriesPortType seriesPortType) {
		this.seriesPortType = seriesPortType;
	}

	@Deprecated
	public SeriesFullResponse get(SeriesFullRequest request) {
		return seriesPortType.getSeriesFull(request);
	}

	@Deprecated
	public SeriesBaseResponse search(SeriesBaseRequest request) {
		return seriesPortType.getSeriesBase(request);
	}

}
