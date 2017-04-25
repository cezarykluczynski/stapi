package com.cezarykluczynski.stapi.server.episode.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeRestReader

class EpisodeRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private EpisodeRestReader episodeRestReaderMock

	private EpisodeRestEndpoint episodeRestEndpoint

	void setup() {
		episodeRestReaderMock = Mock()
		episodeRestEndpoint = new EpisodeRestEndpoint(episodeRestReaderMock)
	}

	void "passes get call to EpisodeRestReader"() {
		given:
		EpisodeFullResponse episodeFullResponse = Mock()

		when:
		EpisodeFullResponse episodeFullResponseOutput = episodeRestEndpoint.getEpisode(UID)

		then:
		1 * episodeRestReaderMock.readFull(UID) >> episodeFullResponse
		episodeFullResponseOutput == episodeFullResponse
	}

	void "passes search get call to EpisodeRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		EpisodeBaseResponse episodeResponse = Mock()

		when:
		EpisodeBaseResponse episodeResponseOutput = episodeRestEndpoint.searchEpisode(pageAwareBeanParams)

		then:
		1 * episodeRestReaderMock.readBase(_ as EpisodeRestBeanParams) >> { EpisodeRestBeanParams episodeRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			episodeResponse
		}
		episodeResponseOutput == episodeResponse
	}

	void "passes search post call to EpisodeRestReader"() {
		given:
		EpisodeRestBeanParams episodeRestBeanParams = new EpisodeRestBeanParams(title: TITLE)
		EpisodeBaseResponse episodeResponse = Mock()

		when:
		EpisodeBaseResponse episodeResponseOutput = episodeRestEndpoint.searchEpisode(episodeRestBeanParams)

		then:
		1 * episodeRestReaderMock.readBase(episodeRestBeanParams as EpisodeRestBeanParams) >> { EpisodeRestBeanParams params ->
			assert params.title == TITLE
			episodeResponse
		}
		episodeResponseOutput == episodeResponse
	}

}
