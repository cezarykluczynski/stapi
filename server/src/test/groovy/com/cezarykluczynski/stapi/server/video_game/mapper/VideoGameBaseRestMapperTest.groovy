package com.cezarykluczynski.stapi.server.video_game.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBase
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.server.video_game.dto.VideoGameRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class VideoGameBaseRestMapperTest extends AbstractVideoGameMapperTest {

	private VideoGameBaseRestMapper videoGameBaseRestMapper

	void setup() {
		videoGameBaseRestMapper = Mappers.getMapper(VideoGameBaseRestMapper)
	}

	void "maps VideoGameRestBeanParams to VideoGameRequestDTO"() {
		given:
		VideoGameRestBeanParams videoGameRestBeanParams = new VideoGameRestBeanParams(
				uid: UID,
				title: TITLE,
				releaseDateFrom: RELEASE_DATE_FROM,
				releaseDateTo: RELEASE_DATE_TO)

		when:
		VideoGameRequestDTO videoGameRequestDTO = videoGameBaseRestMapper.mapBase videoGameRestBeanParams

		then:
		videoGameRequestDTO.uid == UID
		videoGameRequestDTO.title == TITLE
		videoGameRequestDTO.releaseDateFrom == RELEASE_DATE_FROM
		videoGameRequestDTO.releaseDateTo == RELEASE_DATE_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		VideoGame videoGame = createVideoGame()

		when:
		VideoGameBase videoGameBase = videoGameBaseRestMapper.mapBase(Lists.newArrayList(videoGame))[0]

		then:
		videoGameBase.uid == UID
		videoGameBase.title == TITLE
		videoGameBase.releaseDate == RELEASE_DATE
		videoGameBase.stardateFrom == STARDATE_FROM
		videoGameBase.stardateTo == STARDATE_TO
		videoGameBase.yearFrom == YEAR_FROM
		videoGameBase.yearTo == YEAR_TO
		videoGameBase.systemRequirements == SYSTEM_REQUIREMENTS
	}

}
