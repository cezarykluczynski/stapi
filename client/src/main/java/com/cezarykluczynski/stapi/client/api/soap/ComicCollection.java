package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionPortType;

public class ComicCollection {

	private final ComicCollectionPortType comicCollectionPortType;

	public ComicCollection(ComicCollectionPortType comicCollectionPortType) {
		this.comicCollectionPortType = comicCollectionPortType;
	}

	@Deprecated
	public ComicCollectionFullResponse get(ComicCollectionFullRequest request) {
		return comicCollectionPortType.getComicCollectionFull(request);
	}

	@Deprecated
	public ComicCollectionBaseResponse search(ComicCollectionBaseRequest request) {
		return comicCollectionPortType.getComicCollectionBase(request);
	}

}
