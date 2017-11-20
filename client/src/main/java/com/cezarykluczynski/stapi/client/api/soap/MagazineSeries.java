package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesPortType;

public class MagazineSeries {

	private final MagazineSeriesPortType magazineSeriesPortType;

	private final ApiKeySupplier apiKeySupplier;

	public MagazineSeries(MagazineSeriesPortType magazineSeriesPortType, ApiKeySupplier apiKeySupplier) {
		this.magazineSeriesPortType = magazineSeriesPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public MagazineSeriesFullResponse get(MagazineSeriesFullRequest request) {
		apiKeySupplier.supply(request);
		return magazineSeriesPortType.getMagazineSeriesFull(request);
	}

	public MagazineSeriesBaseResponse search(MagazineSeriesBaseRequest request) {
		apiKeySupplier.supply(request);
		return magazineSeriesPortType.getMagazineSeriesBase(request);
	}

}
