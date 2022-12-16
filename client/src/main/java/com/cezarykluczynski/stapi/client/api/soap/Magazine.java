package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazinePortType;

public class Magazine {

	private final MagazinePortType magazinePortType;

	public Magazine(MagazinePortType magazinePortType) {
		this.magazinePortType = magazinePortType;
	}

	@Deprecated
	public MagazineFullResponse get(MagazineFullRequest request) {
		return magazinePortType.getMagazineFull(request);
	}

	@Deprecated
	public MagazineBaseResponse search(MagazineBaseRequest request) {
		return magazinePortType.getMagazineBase(request);
	}

}
