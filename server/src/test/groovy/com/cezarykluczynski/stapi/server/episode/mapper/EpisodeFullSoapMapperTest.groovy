package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFull
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import org.mapstruct.factory.Mappers

class EpisodeFullSoapMapperTest extends AbstractEpisodeMapperTest {

	private EpisodeFullSoapMapper episodeFullSoapMapper

	void setup() {
		episodeFullSoapMapper = Mappers.getMapper(EpisodeFullSoapMapper)
	}

	void "maps SOAP EpisodeFullRequest to EpisodeBaseRequestDTO"() {
		given:
		EpisodeFullRequest episodeFullRequest = new EpisodeFullRequest(uid: UID)

		when:
		EpisodeRequestDTO episodeRequestDTO = episodeFullSoapMapper.mapFull episodeFullRequest

		then:
		episodeRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Episode episode = createEpisode()

		when:
		EpisodeFull episodeFull = episodeFullSoapMapper.mapFull(episode)

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
		episodeFull.yearFrom.toInteger() == YEAR_FROM
		episodeFull.yearTo.toInteger() == YEAR_TO
		episodeFull.usAirDate == US_AIR_DATE_XML
		episodeFull.finalScriptDate == FINAL_SCRIPT_DATE_XML
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
