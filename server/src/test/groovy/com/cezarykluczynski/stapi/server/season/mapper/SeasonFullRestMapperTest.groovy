package com.cezarykluczynski.stapi.server.season.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFull
import com.cezarykluczynski.stapi.model.season.entity.Season
import org.mapstruct.factory.Mappers

class SeasonFullRestMapperTest extends AbstractSeasonMapperTest {

	private SeasonFullRestMapper seasonFullRestMapper

	void setup() {
		seasonFullRestMapper = Mappers.getMapper(SeasonFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Season dBSeason = createSeason()

		when:
		SeasonFull seasonFull = seasonFullRestMapper.mapFull(dBSeason)

		then:
		seasonFull.uid == UID
		seasonFull.title == TITLE
		seasonFull.series != null
		seasonFull.seasonNumber == SEASON_NUMBER
		seasonFull.numberOfEpisodes == NUMBER_OF_EPISODES
		seasonFull.episodes.size() == seasonFull.episodes.size()
	}

}
