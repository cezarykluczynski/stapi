package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsPortType;

public class Comics {

	private final ComicsPortType comicsPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Comics(ComicsPortType comicsPortType, ApiKeySupplier apiKeySupplier) {
		this.comicsPortType = comicsPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public ComicsFullResponse get(ComicsFullRequest request) {
		apiKeySupplier.supply(request);
		return comicsPortType.getComicsFull(request);
	}

	public ComicsBaseResponse search(ComicsBaseRequest request) {
		apiKeySupplier.supply(request);
		return comicsPortType.getComicsBase(request);
	}

}
