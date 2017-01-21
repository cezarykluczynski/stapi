package com.cezarykluczynski.stapi.server.episode.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.Episode as RESTEpisode
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.episode.entity.Episode as DBEpisode
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeRestMapper
import com.cezarykluczynski.stapi.server.episode.query.EpisodeRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class EpisodeRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private EpisodeRestQuery episodeRestQueryBuilderMock

	private EpisodeRestMapper episodeRestMapperMock

	private PageMapper pageMapperMock

	private EpisodeRestReader episodeRestReader

	void setup() {
		episodeRestQueryBuilderMock = Mock(EpisodeRestQuery)
		episodeRestMapperMock = Mock(EpisodeRestMapper)
		pageMapperMock = Mock(PageMapper)
		episodeRestReader = new EpisodeRestReader(episodeRestQueryBuilderMock, episodeRestMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into EpisodeResponse"() {
		given:
		List<DBEpisode> dbEpisodeList = Lists.newArrayList()
		Page<DBEpisode> dbEpisodePage = Mock(Page)
		dbEpisodePage.content >> dbEpisodeList
		List<RESTEpisode> soapEpisodeList = Lists.newArrayList(new RESTEpisode(guid: GUID))
		EpisodeRestBeanParams seriesRestBeanParams = Mock(EpisodeRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		EpisodeResponse episodeResponse = episodeRestReader.read(seriesRestBeanParams)

		then:
		1 * episodeRestQueryBuilderMock.query(seriesRestBeanParams) >> dbEpisodePage
		1 * pageMapperMock.fromPageToRestResponsePage(dbEpisodePage) >> responsePage
		1 * episodeRestMapperMock.map(dbEpisodeList) >> soapEpisodeList
		episodeResponse.episodes[0].guid == GUID
		episodeResponse.page == responsePage
	}

}
