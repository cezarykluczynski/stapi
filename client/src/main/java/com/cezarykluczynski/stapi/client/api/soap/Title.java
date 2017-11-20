package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TitlePortType;

public class Title {

	private final TitlePortType titlePortType;

	private final ApiKeySupplier apiKeySupplier;

	public Title(TitlePortType titlePortType, ApiKeySupplier apiKeySupplier) {
		this.titlePortType = titlePortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public TitleFullResponse get(TitleFullRequest request) {
		apiKeySupplier.supply(request);
		return titlePortType.getTitleFull(request);
	}

	public TitleBaseResponse search(TitleBaseRequest request) {
		apiKeySupplier.supply(request);
		return titlePortType.getTitleBase(request);
	}

}

