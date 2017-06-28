package com.cezarykluczynski.stapi.server.season.mapper

import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBase
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeasonBaseSoapMapperTest extends AbstractSeasonMapperTest {

	private SeasonBaseSoapMapper seasonBaseSoapMapper

	void setup() {
		seasonBaseSoapMapper = Mappers.getMapper(SeasonBaseSoapMapper)
	}

	void "maps SOAP SeasonBaseRequest to SeasonRequestDTO"() {
		given:
		SeasonBaseRequest seasonBaseRequest = new SeasonBaseRequest(
				title: TITLE,
				seasonNumber: new IntegerRange(
						from: SEASON_NUMBER_FROM,
						to: SEASON_NUMBER_TO
				),
				numberOfEpisodes: new IntegerRange(
						from: NUMBER_OF_EPISODES_FROM,
						to: NUMBER_OF_EPISODES_TO))

		when:
		SeasonRequestDTO seasonRequestDTO = seasonBaseSoapMapper.mapBase seasonBaseRequest

		then:
		seasonRequestDTO.title == TITLE
		seasonRequestDTO.seasonNumberFrom == SEASON_NUMBER_FROM
		seasonRequestDTO.seasonNumberTo == SEASON_NUMBER_TO
		seasonRequestDTO.numberOfEpisodesFrom == NUMBER_OF_EPISODES_FROM
		seasonRequestDTO.numberOfEpisodesTo == NUMBER_OF_EPISODES_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Season season = createSeason()

		when:
		SeasonBase seasonBase = seasonBaseSoapMapper.mapBase(Lists.newArrayList(season))[0]

		then:
		seasonBase.uid == UID
		seasonBase.title == TITLE
		seasonBase.series != null
		seasonBase.seasonNumber == SEASON_NUMBER
		seasonBase.numberOfEpisodes == NUMBER_OF_EPISODES
	}

}
