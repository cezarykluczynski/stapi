package com.cezarykluczynski.stapi.server.episode.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class EpisodeSoapEndpoint implements EpisodePortType {

	public static final String ADDRESS = "/v1/soap/episode";

	private final EpisodeSoapReader episodeSoapReader;

	public EpisodeSoapEndpoint(EpisodeSoapReader episodeSoapReader) {
		this.episodeSoapReader = episodeSoapReader;
	}

	@Override
	public EpisodeBaseResponse getEpisodeBase(@WebParam(partName = "request", name = "EpisodeBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/episode") EpisodeBaseRequest request) {
		return episodeSoapReader.readBase(request);
	}

	@Override
	public EpisodeFullResponse getEpisodeFull(@WebParam(partName = "request", name = "EpisodeFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/episode") EpisodeFullRequest request) {
		return episodeSoapReader.readFull(request);
	}

}
