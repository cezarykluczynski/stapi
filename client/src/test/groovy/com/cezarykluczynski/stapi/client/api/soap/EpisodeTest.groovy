package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType
import spock.lang.Specification

class EpisodeTest extends Specification {

	private EpisodePortType episodePortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Episode episode

	void setup() {
		episodePortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		episode = new Episode(episodePortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		EpisodeBaseRequest episodeBaseRequest = Mock()
		EpisodeBaseResponse episodeBaseResponse = Mock()

		when:
		EpisodeBaseResponse episodeBaseResponseOutput = episode.search(episodeBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(episodeBaseRequest)
		1 * episodePortTypeMock.getEpisodeBase(episodeBaseRequest) >> episodeBaseResponse
		0 * _
		episodeBaseResponse == episodeBaseResponseOutput
	}

	void "searches entities"() {
		given:
		EpisodeFullRequest episodeFullRequest = Mock()
		EpisodeFullResponse episodeFullResponse = Mock()

		when:
		EpisodeFullResponse episodeFullResponseOutput = episode.get(episodeFullRequest)

		then:
		1 * apiKeySupplierMock.supply(episodeFullRequest)
		1 * episodePortTypeMock.getEpisodeFull(episodeFullRequest) >> episodeFullResponse
		0 * _
		episodeFullResponse == episodeFullResponseOutput
	}

}
