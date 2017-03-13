package com.cezarykluczynski.stapi.server.episode.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeResponse;
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class EpisodeSoapEndpoint implements EpisodePortType {

	private EpisodeSoapReader seriesSoapReader;

	public EpisodeSoapEndpoint(EpisodeSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public EpisodeResponse getEpisodes(@WebParam(partName = "request", name = "EpisodeRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/episode") EpisodeRequest request) {
		return seriesSoapReader.readBase(request);
	}

}
