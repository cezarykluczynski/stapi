package com.cezarykluczynski.stapi.server.video_game.mapper

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFull
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import org.mapstruct.factory.Mappers

class VideoGameFullSoapMapperTest extends AbstractVideoGameMapperTest {

	private VideoGameFullSoapMapper videoGameFullSoapMapper

	void setup() {
		videoGameFullSoapMapper = Mappers.getMapper(VideoGameFullSoapMapper)
	}

	void "maps SOAP VideoGameFullRequest to VideoGameBaseRequestDTO"() {
		given:
		VideoGameFullRequest videoGameFullRequest = new VideoGameFullRequest(uid: UID)

		when:
		VideoGameRequestDTO videoGameRequestDTO = videoGameFullSoapMapper.mapFull videoGameFullRequest

		then:
		videoGameRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		VideoGame videoGame = createVideoGame()

		when:
		VideoGameFull videoGameFull = videoGameFullSoapMapper.mapFull(videoGame)

		then:
		videoGameFull.uid == UID
		videoGameFull.title == TITLE
		videoGameFull.releaseDate == RELEASE_DATE_XML
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
