package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsPortType;

public class Comics {

	private final ComicsPortType comicsPortType;

	public Comics(ComicsPortType comicsPortType) {
		this.comicsPortType = comicsPortType;
	}

	@Deprecated
	public ComicsFullResponse get(ComicsFullRequest request) {
		return comicsPortType.getComicsFull(request);
	}

	@Deprecated
	public ComicsBaseResponse search(ComicsBaseRequest request) {
		return comicsPortType.getComicsBase(request);
	}

}
