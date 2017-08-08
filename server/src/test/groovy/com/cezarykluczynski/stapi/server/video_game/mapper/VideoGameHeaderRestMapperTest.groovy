package com.cezarykluczynski.stapi.server.video_game.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameHeader
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class VideoGameHeaderRestMapperTest extends AbstractVideoGameMapperTest {

	private VideoGameHeaderRestMapper videoGameHeaderRestMapper

	void setup() {
		videoGameHeaderRestMapper = Mappers.getMapper(VideoGameHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		VideoGame videoGame = new VideoGame(
				uid: UID,
				title: TITLE)

		when:
		VideoGameHeader videoGameHeader = videoGameHeaderRestMapper.map(Lists.newArrayList(videoGame))[0]

		then:
		videoGameHeader.uid == UID
		videoGameHeader.title == TITLE
	}

}
