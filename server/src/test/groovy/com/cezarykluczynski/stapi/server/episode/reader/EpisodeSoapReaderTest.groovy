package com.cezarykluczynski.stapi.server.episode.reader

import com.cezarykluczynski.stapi.client.v1.soap.Episode as SOAPEpisode
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.episode.entity.Episode as DBEpisode
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeSoapMapper
import com.cezarykluczynski.stapi.server.episode.query.EpisodeSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class EpisodeSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private EpisodeSoapQuery episodeSoapQueryBuilderMock

	private EpisodeSoapMapper episodeSoapMapperMock

	private PageMapper pageMapperMock

	private EpisodeSoapReader episodeSoapReader

	void setup() {
		episodeSoapQueryBuilderMock = Mock(EpisodeSoapQuery)
		episodeSoapMapperMock = Mock(EpisodeSoapMapper)
		pageMapperMock = Mock(PageMapper)
		episodeSoapReader = new EpisodeSoapReader(episodeSoapQueryBuilderMock, episodeSoapMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into EpisodeResponse"() {
		given:
		List<DBEpisode> dbEpisodeList = Lists.newArrayList()
		Page<DBEpisode> dbEpisodePage = Mock(Page)
		dbEpisodePage.content >> dbEpisodeList
		List<SOAPEpisode> soapEpisodeList = Lists.newArrayList(new SOAPEpisode(guid: GUID))
		EpisodeRequest episodeRequest = Mock(EpisodeRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		EpisodeResponse episodeResponse = episodeSoapReader.read(episodeRequest)

		then:
		1 * episodeSoapQueryBuilderMock.query(episodeRequest) >> dbEpisodePage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbEpisodePage) >> responsePage
		1 * episodeSoapMapperMock.map(dbEpisodeList) >> soapEpisodeList
		episodeResponse.episodes[0].guid == GUID
		episodeResponse.page == responsePage
	}

}
