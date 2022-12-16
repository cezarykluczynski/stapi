package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripPortType;

public class ComicStrip {

	private final ComicStripPortType comicStripPortType;

	public ComicStrip(ComicStripPortType comicStripPortType) {
		this.comicStripPortType = comicStripPortType;
	}

	@Deprecated
	public ComicStripFullResponse get(ComicStripFullRequest request) {
		return comicStripPortType.getComicStripFull(request);
	}

	@Deprecated
	public ComicStripBaseResponse search(ComicStripBaseRequest request) {
		return comicStripPortType.getComicStripBase(request);
	}

}
