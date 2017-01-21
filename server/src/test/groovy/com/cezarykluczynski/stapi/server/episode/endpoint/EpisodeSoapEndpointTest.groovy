package com.cezarykluczynski.stapi.server.episode.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeResponse
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeSoapReader
import spock.lang.Specification

class EpisodeSoapEndpointTest extends Specification {

	private EpisodeSoapReader episodeSoapReaderMock

	private EpisodeSoapEndpoint episodeSoapEndpoint

	void setup() {
		episodeSoapReaderMock = Mock(EpisodeSoapReader)
		episodeSoapEndpoint = new EpisodeSoapEndpoint(episodeSoapReaderMock)
	}

	void "passes call to EpisodeSoapReader"() {
		given:
		EpisodeRequest episodeRequest = Mock(EpisodeRequest)
		EpisodeResponse episodeResponse = Mock(EpisodeResponse)

		when:
		EpisodeResponse episodeResponseResult = episodeSoapEndpoint.getEpisodes(episodeRequest)

		then:
		1 * episodeSoapReaderMock.read(episodeRequest) >> episodeResponse
		episodeResponseResult == episodeResponse
	}

}
