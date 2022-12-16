package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGamePortType;

public class VideoGame {

	private final VideoGamePortType animalPortType;

	public VideoGame(VideoGamePortType animalPortType) {
		this.animalPortType = animalPortType;
	}

	@Deprecated
	public VideoGameFullResponse get(VideoGameFullRequest request) {
		return animalPortType.getVideoGameFull(request);
	}

	@Deprecated
	public VideoGameBaseResponse search(VideoGameBaseRequest request) {
		return animalPortType.getVideoGameBase(request);
	}

}

