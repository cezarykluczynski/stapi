package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonPortType;

public class Season {

	private final SeasonPortType seasonPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Season(SeasonPortType seasonPortType, ApiKeySupplier apiKeySupplier) {
		this.seasonPortType = seasonPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public SeasonFullResponse get(SeasonFullRequest request) {
		apiKeySupplier.supply(request);
		return seasonPortType.getSeasonFull(request);
	}

	public SeasonBaseResponse search(SeasonBaseRequest request) {
		apiKeySupplier.supply(request);
		return seasonPortType.getSeasonBase(request);
	}

}
