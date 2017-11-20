package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionPortType;

public class ComicCollection {

	private final ComicCollectionPortType comicCollectionPortType;

	private final ApiKeySupplier apiKeySupplier;

	public ComicCollection(ComicCollectionPortType comicCollectionPortType, ApiKeySupplier apiKeySupplier) {
		this.comicCollectionPortType = comicCollectionPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public ComicCollectionFullResponse get(ComicCollectionFullRequest request) {
		apiKeySupplier.supply(request);
		return comicCollectionPortType.getComicCollectionFull(request);
	}

	public ComicCollectionBaseResponse search(ComicCollectionBaseRequest request) {
		apiKeySupplier.supply(request);
		return comicCollectionPortType.getComicCollectionBase(request);
	}

}
