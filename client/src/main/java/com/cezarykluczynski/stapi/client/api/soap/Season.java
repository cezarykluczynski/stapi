package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonPortType;

public class Season {

	private final SeasonPortType seasonPortType;

	public Season(SeasonPortType seasonPortType) {
		this.seasonPortType = seasonPortType;
	}

	@Deprecated
	public SeasonFullResponse get(SeasonFullRequest request) {
		return seasonPortType.getSeasonFull(request);
	}

	@Deprecated
	public SeasonBaseResponse search(SeasonBaseRequest request) {
		return seasonPortType.getSeasonBase(request);
	}

}
