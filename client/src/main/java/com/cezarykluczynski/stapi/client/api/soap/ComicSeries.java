package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;

public class ComicSeries {

	private final ComicSeriesPortType comicSeriesPortType;

	public ComicSeries(ComicSeriesPortType comicSeriesPortType) {
		this.comicSeriesPortType = comicSeriesPortType;
	}

	@Deprecated
	public ComicSeriesFullResponse get(ComicSeriesFullRequest request) {
		return comicSeriesPortType.getComicSeriesFull(request);
	}

	@Deprecated
	public ComicSeriesBaseResponse search(ComicSeriesBaseRequest request) {
		return comicSeriesPortType.getComicSeriesBase(request);
	}

}
