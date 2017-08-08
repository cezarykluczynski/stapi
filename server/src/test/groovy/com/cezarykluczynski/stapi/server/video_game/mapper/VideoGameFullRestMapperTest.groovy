package com.cezarykluczynski.stapi.server.video_game.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFull
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import org.mapstruct.factory.Mappers

class VideoGameFullRestMapperTest extends AbstractVideoGameMapperTest {

	private VideoGameFullRestMapper videoGameFullRestMapper

	void setup() {
		videoGameFullRestMapper = Mappers.getMapper(VideoGameFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		VideoGame videoGame = createVideoGame()

		when:
		VideoGameFull videoGameFull = videoGameFullRestMapper.mapFull(videoGame)

		then:
		videoGameFull.uid == UID
		videoGameFull.title == TITLE
		videoGameFull.releaseDate == RELEASE_DATE
		videoGameFull.stardateFrom == STARDATE_FROM
		videoGameFull.stardateTo == STARDATE_TO
		videoGameFull.yearFrom == YEAR_FROM
		videoGameFull.yearTo == YEAR_TO
		videoGameFull.systemRequirements == SYSTEM_REQUIREMENTS
		videoGameFull.publishers.size() == videoGame.publishers.size()
		videoGameFull.developers.size() == videoGame.developers.size()
		videoGameFull.platforms.size() == videoGame.platforms.size()
		videoGameFull.genres.size() == videoGame.genres.size()
		videoGameFull.ratings.size() == videoGame.ratings.size()
		videoGameFull.references.size() == videoGame.references.size()
	}

}
