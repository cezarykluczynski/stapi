package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGamePortType;

public class VideoGame {

	private final VideoGamePortType animalPortType;

	private final ApiKeySupplier apiKeySupplier;

	public VideoGame(VideoGamePortType animalPortType, ApiKeySupplier apiKeySupplier) {
		this.animalPortType = animalPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public VideoGameFullResponse get(VideoGameFullRequest request) {
		apiKeySupplier.supply(request);
		return animalPortType.getVideoGameFull(request);
	}

	public VideoGameBaseResponse search(VideoGameBaseRequest request) {
		apiKeySupplier.supply(request);
		return animalPortType.getVideoGameBase(request);
	}

}

