package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TitlePortType;

public class Title {

	private final TitlePortType titlePortType;

	public Title(TitlePortType titlePortType) {
		this.titlePortType = titlePortType;
	}

	@Deprecated
	public TitleFullResponse get(TitleFullRequest request) {
		return titlePortType.getTitleFull(request);
	}

	@Deprecated
	public TitleBaseResponse search(TitleBaseRequest request) {
		return titlePortType.getTitleBase(request);
	}

}

