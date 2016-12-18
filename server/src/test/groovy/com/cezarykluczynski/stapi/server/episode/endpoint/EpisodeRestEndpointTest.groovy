package com.cezarykluczynski.stapi.server.episode.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeRestReader

class EpisodeRestEndpointTest extends AbstractRestEndpointTest {

	private EpisodeRestReader episodeRestReaderMock

	private EpisodeRestEndpoint episodeRestEndpoint

	def setup() {
		episodeRestReaderMock = Mock(EpisodeRestReader)
		episodeRestEndpoint = new EpisodeRestEndpoint(episodeRestReaderMock)
	}

	def "passes get call to EpisodeRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams) {
			getPageNumber() >> PAGE_NUMBER
			getPageSize() >> PAGE_SIZE
		}
		EpisodeResponse episodeResponse = Mock(EpisodeResponse)

		when:
		EpisodeResponse episodeResponseOutput = episodeRestEndpoint.getEpisodes(pageAwareBeanParams)

		then:
		1 * episodeRestReaderMock.read(_ as EpisodeRestBeanParams) >> { EpisodeRestBeanParams episodeRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			return episodeResponse
		}
		episodeResponseOutput == episodeResponse
	}

	def "passes post call to EpisodeRestReader"() {
		given:
		EpisodeRestBeanParams episodeRestBeanParams = new EpisodeRestBeanParams()
		EpisodeResponse episodeResponse = Mock(EpisodeResponse)

		when:
		EpisodeResponse episodeResponseOutput = episodeRestEndpoint.searchEpisodes(episodeRestBeanParams)

		then:
		1 * episodeRestReaderMock.read(episodeRestBeanParams as EpisodeRestBeanParams) >> { EpisodeRestBeanParams params ->
			return episodeResponse
		}
		episodeResponseOutput == episodeResponse
	}

}
