package com.cezarykluczynski.stapi.server.episode.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullResponse
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeSoapReader
import spock.lang.Specification

class EpisodeSoapEndpointTest extends Specification {

	private EpisodeSoapReader episodeSoapReaderMock

	private EpisodeSoapEndpoint episodeSoapEndpoint

	void setup() {
		episodeSoapReaderMock = Mock()
		episodeSoapEndpoint = new EpisodeSoapEndpoint(episodeSoapReaderMock)
	}

	void "passes base call to EpisodeSoapReader"() {
		given:
		EpisodeBaseRequest episodeBaseRequest = Mock()
		EpisodeBaseResponse episodeBaseResponse = Mock()

		when:
		EpisodeBaseResponse episodeResponseResult = episodeSoapEndpoint.getEpisodeBase(episodeBaseRequest)

		then:
		1 * episodeSoapReaderMock.readBase(episodeBaseRequest) >> episodeBaseResponse
		episodeResponseResult == episodeBaseResponse
	}

	void "passes full call to EpisodeSoapReader"() {
		given:
		EpisodeFullRequest episodeFullRequest = Mock()
		EpisodeFullResponse episodeFullResponse = Mock()

		when:
		EpisodeFullResponse episodeResponseResult = episodeSoapEndpoint.getEpisodeFull(episodeFullRequest)

		then:
		1 * episodeSoapReaderMock.readFull(episodeFullRequest) >> episodeFullResponse
		episodeResponseResult == episodeFullResponse
	}

}
