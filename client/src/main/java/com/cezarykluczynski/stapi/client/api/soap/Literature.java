package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LiteraturePortType;

public class Literature {

	private final LiteraturePortType literaturePortType;

	private final ApiKeySupplier apiKeySupplier;

	public Literature(LiteraturePortType literaturePortType, ApiKeySupplier apiKeySupplier) {
		this.literaturePortType = literaturePortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public LiteratureFullResponse get(LiteratureFullRequest request) {
		apiKeySupplier.supply(request);
		return literaturePortType.getLiteratureFull(request);
	}

	public LiteratureBaseResponse search(LiteratureBaseRequest request) {
		apiKeySupplier.supply(request);
		return literaturePortType.getLiteratureBase(request);
	}

}
