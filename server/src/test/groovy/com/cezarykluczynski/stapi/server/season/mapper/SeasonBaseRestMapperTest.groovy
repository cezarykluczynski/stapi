package com.cezarykluczynski.stapi.server.season.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBase
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.server.season.dto.SeasonRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeasonBaseRestMapperTest extends AbstractSeasonMapperTest {

	private SeasonBaseRestMapper seasonBaseRestMapper

	void setup() {
		seasonBaseRestMapper = Mappers.getMapper(SeasonBaseRestMapper)
	}

	void "maps SeasonRestBeanParams to SeasonRequestDTO"() {
		given:
		SeasonRestBeanParams seasonRestBeanParams = new SeasonRestBeanParams(
				title: TITLE,
				seasonNumberFrom: SEASON_NUMBER_FROM,
				seasonNumberTo: SEASON_NUMBER_TO,
				numberOfEpisodesFrom: NUMBER_OF_EPISODES_FROM,
				numberOfEpisodesTo: NUMBER_OF_EPISODES_TO)

		when:
		SeasonRequestDTO seasonRequestDTO = seasonBaseRestMapper.mapBase seasonRestBeanParams

		then:
		seasonRequestDTO.title == TITLE
		seasonRequestDTO.seasonNumberFrom == SEASON_NUMBER_FROM
		seasonRequestDTO.seasonNumberTo == SEASON_NUMBER_TO
		seasonRequestDTO.numberOfEpisodesFrom == NUMBER_OF_EPISODES_FROM
		seasonRequestDTO.numberOfEpisodesTo == NUMBER_OF_EPISODES_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		Season season = createSeason()

		when:
		SeasonBase seasonBase = seasonBaseRestMapper.mapBase(Lists.newArrayList(season))[0]

		then:
		seasonBase.uid == UID
		seasonBase.title == TITLE
		seasonBase.series != null
		seasonBase.seasonNumber == SEASON_NUMBER
		seasonBase.numberOfEpisodes == NUMBER_OF_EPISODES
	}

}
