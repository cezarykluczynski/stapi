package com.cezarykluczynski.stapi.server.video_game.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBase
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFull
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullResponse
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseSoapMapper
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullSoapMapper
import com.cezarykluczynski.stapi.server.video_game.query.VideoGameSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class VideoGameSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private VideoGameSoapQuery videoGameSoapQueryBuilderMock

	private VideoGameBaseSoapMapper videoGameBaseSoapMapperMock

	private VideoGameFullSoapMapper videoGameFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private VideoGameSoapReader videoGameSoapReader

	void setup() {
		videoGameSoapQueryBuilderMock = Mock()
		videoGameBaseSoapMapperMock = Mock()
		videoGameFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		videoGameSoapReader = new VideoGameSoapReader(videoGameSoapQueryBuilderMock, videoGameBaseSoapMapperMock, videoGameFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<VideoGame> videoGameList = Lists.newArrayList()
		Page<VideoGame> videoGamePage = Mock()
		List<VideoGameBase> soapVideoGameList = Lists.newArrayList(new VideoGameBase(uid: UID))
		VideoGameBaseRequest videoGameBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		VideoGameBaseResponse videoGameResponse = videoGameSoapReader.readBase(videoGameBaseRequest)

		then:
		1 * videoGameSoapQueryBuilderMock.query(videoGameBaseRequest) >> videoGamePage
		1 * videoGamePage.content >> videoGameList
		1 * pageMapperMock.fromPageToSoapResponsePage(videoGamePage) >> responsePage
		1 * videoGameBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * videoGameBaseSoapMapperMock.mapBase(videoGameList) >> soapVideoGameList
		0 * _
		videoGameResponse.videoGames[0].uid == UID
		videoGameResponse.page == responsePage
		videoGameResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		VideoGameFull videoGameFull = new VideoGameFull(uid: UID)
		VideoGame videoGame = Mock()
		Page<VideoGame> videoGamePage = Mock()
		VideoGameFullRequest videoGameFullRequest = new VideoGameFullRequest(uid: UID)

		when:
		VideoGameFullResponse videoGameFullResponse = videoGameSoapReader.readFull(videoGameFullRequest)

		then:
		1 * videoGameSoapQueryBuilderMock.query(videoGameFullRequest) >> videoGamePage
		1 * videoGamePage.content >> Lists.newArrayList(videoGame)
		1 * videoGameFullSoapMapperMock.mapFull(videoGame) >> videoGameFull
		0 * _
		videoGameFullResponse.videoGame.uid == UID
	}

	void "requires UID in full request"() {
		given:
		VideoGameFullRequest videoGameFullRequest = Mock()

		when:
		videoGameSoapReader.readFull(videoGameFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
