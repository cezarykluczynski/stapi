package com.cezarykluczynski.stapi.server.video_game.mapper

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameHeader
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class VideoGameHeaderSoapMapperTest extends AbstractVideoGameMapperTest {

	private VideoGameHeaderSoapMapper videoGameHeaderSoapMapper

	void setup() {
		videoGameHeaderSoapMapper = Mappers.getMapper(VideoGameHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		VideoGame videoGame = new VideoGame(
				uid: UID,
				title: TITLE)

		when:
		VideoGameHeader videoGameHeader = videoGameHeaderSoapMapper.map(Lists.newArrayList(videoGame))[0]

		then:
		videoGameHeader.uid == UID
		videoGameHeader.title == TITLE
	}

}
