package com.cezarykluczynski.stapi.server.season.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SeasonFull
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO
import com.cezarykluczynski.stapi.model.season.entity.Season
import org.mapstruct.factory.Mappers

class SeasonFullSoapMapperTest extends AbstractSeasonMapperTest {

	private SeasonFullSoapMapper seasonFullSoapMapper

	void setup() {
		seasonFullSoapMapper = Mappers.getMapper(SeasonFullSoapMapper)
	}

	void "maps SOAP SeasonFullRequest to SeasonBaseRequestDTO"() {
		given:
		SeasonFullRequest seasonFullRequest = new SeasonFullRequest(uid: UID)

		when:
		SeasonRequestDTO seasonRequestDTO = seasonFullSoapMapper.mapFull seasonFullRequest

		then:
		seasonRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Season season = createSeason()

		when:
		SeasonFull seasonFull = seasonFullSoapMapper.mapFull(season)

		then:
		seasonFull.uid == UID
		seasonFull.title == TITLE
		seasonFull.series != null
		seasonFull.seasonNumber == SEASON_NUMBER
		seasonFull.numberOfEpisodes == NUMBER_OF_EPISODES
		seasonFull.episodes.size() == seasonFull.episodes.size()
	}

}
