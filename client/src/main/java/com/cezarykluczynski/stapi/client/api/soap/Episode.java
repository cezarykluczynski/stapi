package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;

public class Episode {

	private final EpisodePortType episodePortType;

	public Episode(EpisodePortType episodePortType) {
		this.episodePortType = episodePortType;
	}

	@Deprecated
	public EpisodeFullResponse get(EpisodeFullRequest request) {
		return episodePortType.getEpisodeFull(request);
	}

	@Deprecated
	public EpisodeBaseResponse search(EpisodeBaseRequest request) {
		return episodePortType.getEpisodeBase(request);
	}

}
