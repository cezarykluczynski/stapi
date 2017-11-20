package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;

public class Episode {

	private final EpisodePortType episodePortType;

	private final ApiKeySupplier apiKeySupplier;

	public Episode(EpisodePortType episodePortType, ApiKeySupplier apiKeySupplier) {
		this.episodePortType = episodePortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public EpisodeFullResponse get(EpisodeFullRequest request) {
		apiKeySupplier.supply(request);
		return episodePortType.getEpisodeFull(request);
	}

	public EpisodeBaseResponse search(EpisodeBaseRequest request) {
		apiKeySupplier.supply(request);
		return episodePortType.getEpisodeBase(request);
	}

}
