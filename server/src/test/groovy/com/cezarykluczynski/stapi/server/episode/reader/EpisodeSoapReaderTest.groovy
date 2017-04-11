package com.cezarykluczynski.stapi.server.episode.reader

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBase
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFull
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingGUIDException
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullSoapMapper
import com.cezarykluczynski.stapi.server.episode.query.EpisodeSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class EpisodeSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private EpisodeSoapQuery episodeSoapQueryBuilderMock

	private EpisodeBaseSoapMapper episodeBaseSoapMapperMock

	private EpisodeFullSoapMapper episodeFullSoapMapperMock

	private PageMapper pageMapperMock

	private EpisodeSoapReader episodeSoapReader

	void setup() {
		episodeSoapQueryBuilderMock = Mock()
		episodeBaseSoapMapperMock = Mock()
		episodeFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		episodeSoapReader = new EpisodeSoapReader(episodeSoapQueryBuilderMock, episodeBaseSoapMapperMock, episodeFullSoapMapperMock, pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Episode> episodeList = Lists.newArrayList()
		Page<Episode> episodePage = Mock()
		List<EpisodeBase> soapEpisodeList = Lists.newArrayList(new EpisodeBase(guid: GUID))
		EpisodeBaseRequest episodeBaseRequest = Mock()
		ResponsePage responsePage = Mock()

		when:
		EpisodeBaseResponse episodeResponse = episodeSoapReader.readBase(episodeBaseRequest)

		then:
		1 * episodeSoapQueryBuilderMock.query(episodeBaseRequest) >> episodePage
		1 * episodePage.content >> episodeList
		1 * pageMapperMock.fromPageToSoapResponsePage(episodePage) >> responsePage
		1 * episodeBaseSoapMapperMock.mapBase(episodeList) >> soapEpisodeList
		episodeResponse.episodes[0].guid == GUID
		episodeResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		EpisodeFull episodeFull = new EpisodeFull(guid: GUID)
		Episode episode = Mock()
		Page<Episode> episodePage = Mock()
		EpisodeFullRequest episodeFullRequest = new EpisodeFullRequest(guid: GUID)

		when:
		EpisodeFullResponse episodeFullResponse = episodeSoapReader.readFull(episodeFullRequest)

		then:
		1 * episodeSoapQueryBuilderMock.query(episodeFullRequest) >> episodePage
		1 * episodePage.content >> Lists.newArrayList(episode)
		1 * episodeFullSoapMapperMock.mapFull(episode) >> episodeFull
		episodeFullResponse.episode.guid == GUID
	}

	void "requires GUID in full request"() {
		given:
		EpisodeFullRequest episodeFullRequest = Mock()

		when:
		episodeSoapReader.readFull(episodeFullRequest)

		then:
		thrown(MissingGUIDException)
	}

}
