package com.cezarykluczynski.stapi.client.api.soap;


import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;

public class ComicSeries {

	private final ComicSeriesPortType comicSeriesPortType;

	private final ApiKeySupplier apiKeySupplier;

	public ComicSeries(ComicSeriesPortType comicSeriesPortType, ApiKeySupplier apiKeySupplier) {
		this.comicSeriesPortType = comicSeriesPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public ComicSeriesFullResponse get(ComicSeriesFullRequest request) {
		apiKeySupplier.supply(request);
		return comicSeriesPortType.getComicSeriesFull(request);
	}

	public ComicSeriesBaseResponse search(ComicSeriesBaseRequest request) {
		apiKeySupplier.supply(request);
		return comicSeriesPortType.getComicSeriesBase(request);
	}

}
