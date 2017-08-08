package com.cezarykluczynski.stapi.server.video_game.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBase
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFull
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.video_game.dto.VideoGameRestBeanParams
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseRestMapper
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullRestMapper
import com.cezarykluczynski.stapi.server.video_game.query.VideoGameRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class VideoGameRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private VideoGameRestQuery videoGameRestQueryBuilderMock

	private VideoGameBaseRestMapper videoGameBaseRestMapperMock

	private VideoGameFullRestMapper videoGameFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private VideoGameRestReader videoGameRestReader

	void setup() {
		videoGameRestQueryBuilderMock = Mock()
		videoGameBaseRestMapperMock = Mock()
		videoGameFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		videoGameRestReader = new VideoGameRestReader(videoGameRestQueryBuilderMock, videoGameBaseRestMapperMock, videoGameFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		VideoGameBase videoGameBase = Mock()
		VideoGame videoGame = Mock()
		VideoGameRestBeanParams videoGameRestBeanParams = Mock()
		List<VideoGameBase> restVideoGameList = Lists.newArrayList(videoGameBase)
		List<VideoGame> videoGameList = Lists.newArrayList(videoGame)
		Page<VideoGame> videoGamePage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		VideoGameBaseResponse videoGameResponseOutput = videoGameRestReader.readBase(videoGameRestBeanParams)

		then:
		1 * videoGameRestQueryBuilderMock.query(videoGameRestBeanParams) >> videoGamePage
		1 * pageMapperMock.fromPageToRestResponsePage(videoGamePage) >> responsePage
		1 * videoGameRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * videoGamePage.content >> videoGameList
		1 * videoGameBaseRestMapperMock.mapBase(videoGameList) >> restVideoGameList
		0 * _
		videoGameResponseOutput.videoGames == restVideoGameList
		videoGameResponseOutput.page == responsePage
		videoGameResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		VideoGameFull videoGameFull = Mock()
		VideoGame videoGame = Mock()
		List<VideoGame> videoGameList = Lists.newArrayList(videoGame)
		Page<VideoGame> videoGamePage = Mock()

		when:
		VideoGameFullResponse videoGameResponseOutput = videoGameRestReader.readFull(UID)

		then:
		1 * videoGameRestQueryBuilderMock.query(_ as VideoGameRestBeanParams) >> { VideoGameRestBeanParams videoGameRestBeanParams ->
			assert videoGameRestBeanParams.uid == UID
			videoGamePage
		}
		1 * videoGamePage.content >> videoGameList
		1 * videoGameFullRestMapperMock.mapFull(videoGame) >> videoGameFull
		0 * _
		videoGameResponseOutput.videoGame == videoGameFull
	}

	void "requires UID in full request"() {
		when:
		videoGameRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
