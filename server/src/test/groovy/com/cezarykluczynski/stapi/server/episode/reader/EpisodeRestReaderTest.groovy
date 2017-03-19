package com.cezarykluczynski.stapi.server.episode.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBase
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFull
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullRestMapper
import com.cezarykluczynski.stapi.server.episode.query.EpisodeRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class EpisodeRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private EpisodeRestQuery episodeRestQueryBuilderMock

	private EpisodeBaseRestMapper episodeBaseRestMapperMock

	private EpisodeFullRestMapper episodeFullRestMapperMock

	private PageMapper pageMapperMock

	private EpisodeRestReader episodeRestReader

	void setup() {
		episodeRestQueryBuilderMock = Mock(EpisodeRestQuery)
		episodeBaseRestMapperMock = Mock(EpisodeBaseRestMapper)
		episodeFullRestMapperMock = Mock(EpisodeFullRestMapper)
		pageMapperMock = Mock(PageMapper)
		episodeRestReader = new EpisodeRestReader(episodeRestQueryBuilderMock, episodeBaseRestMapperMock, episodeFullRestMapperMock, pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		EpisodeRestBeanParams episodeRestBeanParams = Mock(EpisodeRestBeanParams)
		List<EpisodeBase> restEpisodeList = Lists.newArrayList(Mock(EpisodeBase))
		List<Episode> episodeList = Lists.newArrayList(Mock(Episode))
		Page<Episode> episodePage = Mock(Page)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		EpisodeBaseResponse episodeResponseOutput = episodeRestReader.readBase(episodeRestBeanParams)

		then:
		1 * episodeRestQueryBuilderMock.query(episodeRestBeanParams) >> episodePage
		1 * pageMapperMock.fromPageToRestResponsePage(episodePage) >> responsePage
		1 * episodePage.content >> episodeList
		1 * episodeBaseRestMapperMock.mapBase(episodeList) >> restEpisodeList
		0 * _
		episodeResponseOutput.episodes == restEpisodeList
		episodeResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		EpisodeFull episodeFull = Mock(EpisodeFull)
		Episode episode = Mock(Episode)
		List<Episode> episodeList = Lists.newArrayList(episode)
		Page<Episode> episodePage = Mock(Page)

		when:
		EpisodeFullResponse episodeResponseOutput = episodeRestReader.readFull(GUID)

		then:
		1 * episodeRestQueryBuilderMock.query(_ as EpisodeRestBeanParams) >> { EpisodeRestBeanParams episodeRestBeanParams ->
			assert episodeRestBeanParams.guid == GUID
			episodePage
		}
		1 * episodePage.content >> episodeList
		1 * episodeFullRestMapperMock.mapFull(episode) >> episodeFull
		0 * _
		episodeResponseOutput.episode == episodeFull
	}

}
