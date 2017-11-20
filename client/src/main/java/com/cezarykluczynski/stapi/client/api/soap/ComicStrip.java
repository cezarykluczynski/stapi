package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripPortType;

public class ComicStrip {

	private final ComicStripPortType comicStripPortType;

	private final ApiKeySupplier apiKeySupplier;

	public ComicStrip(ComicStripPortType comicStripPortType, ApiKeySupplier apiKeySupplier) {
		this.comicStripPortType = comicStripPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public ComicStripFullResponse get(ComicStripFullRequest request) {
		apiKeySupplier.supply(request);
		return comicStripPortType.getComicStripFull(request);
	}

	public ComicStripBaseResponse search(ComicStripBaseRequest request) {
		apiKeySupplier.supply(request);
		return comicStripPortType.getComicStripBase(request);
	}

}
