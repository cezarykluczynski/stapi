package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LiteraturePortType;

public class Literature {

	private final LiteraturePortType literaturePortType;

	public Literature(LiteraturePortType literaturePortType) {
		this.literaturePortType = literaturePortType;
	}

	@Deprecated
	public LiteratureFullResponse get(LiteratureFullRequest request) {
		return literaturePortType.getLiteratureFull(request);
	}

	@Deprecated
	public LiteratureBaseResponse search(LiteratureBaseRequest request) {
		return literaturePortType.getLiteratureBase(request);
	}

}
