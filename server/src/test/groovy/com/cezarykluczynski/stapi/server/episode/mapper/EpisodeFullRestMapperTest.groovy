package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFull
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import org.mapstruct.factory.Mappers

class EpisodeFullRestMapperTest extends AbstractEpisodeMapperTest {

	private EpisodeFullRestMapper episodeFullRestMapper

	void setup() {
		episodeFullRestMapper = Mappers.getMapper(EpisodeFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Episode episode = createEpisode()

		when:
		EpisodeFull episodeFull = episodeFullRestMapper.mapFull(episode)

		then:
		episodeFull.uid == UID
		episodeFull.series != null
		episodeFull.season != null
		episodeFull.title == TITLE
		episodeFull.titleGerman == TITLE_GERMAN
		episodeFull.titleItalian == TITLE_ITALIAN
		episodeFull.titleJapanese == TITLE_JAPANESE
		episodeFull.seasonNumber == SEASON_NUMBER
		episodeFull.episodeNumber == EPISODE_NUMBER
		episodeFull.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
		episodeFull.featureLength == FEATURE_LENGTH
		episodeFull.stardateFrom == STARDATE_FROM
		episodeFull.stardateTo == STARDATE_TO
		episodeFull.yearFrom == YEAR_FROM
		episodeFull.yearTo == YEAR_TO
		episodeFull.usAirDate == US_AIR_DATE
		episodeFull.finalScriptDate == FINAL_SCRIPT_DATE
		episodeFull.writers.size() == episode.writers.size()
		episodeFull.teleplayAuthors.size() == episode.teleplayAuthors.size()
		episodeFull.storyAuthors.size() == episode.storyAuthors.size()
		episodeFull.directors.size() == episode.directors.size()
		episodeFull.performers.size() == episode.performers.size()
		episodeFull.stuntPerformers.size() == episode.stuntPerformers.size()
		episodeFull.standInPerformers.size() == episode.standInPerformers.size()
		episodeFull.characters.size() == episode.characters.size()
	}

}
