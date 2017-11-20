package com.cezarykluczynski.stapi.client.api.soap;


import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazinePortType;

public class Magazine {

	private final MagazinePortType magazinePortType;

	private final ApiKeySupplier apiKeySupplier;

	public Magazine(MagazinePortType magazinePortType, ApiKeySupplier apiKeySupplier) {
		this.magazinePortType = magazinePortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public MagazineFullResponse get(MagazineFullRequest request) {
		apiKeySupplier.supply(request);
		return magazinePortType.getMagazineFull(request);
	}

	public MagazineBaseResponse search(MagazineBaseRequest request) {
		apiKeySupplier.supply(request);
		return magazinePortType.getMagazineBase(request);
	}

}
