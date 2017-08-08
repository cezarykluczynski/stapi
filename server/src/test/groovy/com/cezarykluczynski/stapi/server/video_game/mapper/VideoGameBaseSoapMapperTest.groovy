package com.cezarykluczynski.stapi.server.video_game.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBase as VideoGameBase
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class VideoGameBaseSoapMapperTest extends AbstractVideoGameMapperTest {

	private VideoGameBaseSoapMapper videoGameBaseSoapMapper

	void setup() {
		videoGameBaseSoapMapper = Mappers.getMapper(VideoGameBaseSoapMapper)
	}

	void "maps SOAP VideoGameRequest to VideoGameRequestDTO"() {
		given:
		VideoGameBaseRequest videoGameBaseRequest = new VideoGameBaseRequest(
				title: TITLE,
				releaseDate: new DateRange(
						from: RELEASE_DATE_FROM_XML,
						to: RELEASE_DATE_TO_XML,
				))

		when:
		VideoGameRequestDTO videoGameRequestDTO = videoGameBaseSoapMapper.mapBase videoGameBaseRequest

		then:
		videoGameRequestDTO.title == TITLE
		videoGameRequestDTO.releaseDateFrom == RELEASE_DATE_FROM
		videoGameRequestDTO.releaseDateTo == RELEASE_DATE_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		VideoGame videoGame = createVideoGame()

		when:
		VideoGameBase videoGameBase = videoGameBaseSoapMapper.mapBase(Lists.newArrayList(videoGame))[0]

		then:
		videoGameBase.uid == UID
		videoGameBase.title == TITLE
		videoGameBase.releaseDate == RELEASE_DATE_XML
		videoGameBase.stardateFrom == STARDATE_FROM
		videoGameBase.stardateTo == STARDATE_TO
		videoGameBase.yearFrom == YEAR_FROM
		videoGameBase.yearTo == YEAR_TO
		videoGameBase.systemRequirements == SYSTEM_REQUIREMENTS
	}

}
