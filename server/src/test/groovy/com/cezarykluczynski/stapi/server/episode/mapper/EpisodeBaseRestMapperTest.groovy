package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBase
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class EpisodeBaseRestMapperTest extends AbstractEpisodeMapperTest {

	private EpisodeBaseRestMapper episodeBaseRestMapper

	void setup() {
		episodeBaseRestMapper = Mappers.getMapper(EpisodeBaseRestMapper)
	}

	void "maps EpisodeRestBeanParams to EpisodeRequestDTO"() {
		given:
		EpisodeRestBeanParams episodeRestBeanParams = new EpisodeRestBeanParams(
				uid: UID,
				title: TITLE,
				seasonNumberFrom: SEASON_NUMBER_FROM,
				seasonNumberTo: SEASON_NUMBER_TO,
				productionSerialNumber: PRODUCTION_SERIAL_NUMBER,
				featureLength: FEATURE_LENGTH,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				usAirDateFrom: US_AIR_DATE_FROM,
				usAirDateTo: US_AIR_DATE_TO,
				finalScriptDateFrom: FINAL_SCRIPT_DATE_FROM,
				finalScriptDateTo: FINAL_SCRIPT_DATE_TO)

		when:
		EpisodeRequestDTO episodeRequestDTO = episodeBaseRestMapper.mapBase episodeRestBeanParams

		then:
		episodeRequestDTO.uid == UID
		episodeRequestDTO.title == TITLE
		episodeRequestDTO.seasonNumberFrom == SEASON_NUMBER_FROM
		episodeRequestDTO.seasonNumberTo == SEASON_NUMBER_TO
		episodeRequestDTO.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
		episodeRequestDTO.featureLength == FEATURE_LENGTH
		episodeRequestDTO.stardateFrom == STARDATE_FROM
		episodeRequestDTO.stardateTo == STARDATE_TO
		episodeRequestDTO.usAirDateFrom == US_AIR_DATE_FROM
		episodeRequestDTO.usAirDateTo == US_AIR_DATE_TO
		episodeRequestDTO.finalScriptDateFrom == FINAL_SCRIPT_DATE_FROM
		episodeRequestDTO.finalScriptDateTo == FINAL_SCRIPT_DATE_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		Episode episode = createEpisode()

		when:
		EpisodeBase episodeBase = episodeBaseRestMapper.mapBase(Lists.newArrayList(episode))[0]

		then:
		episodeBase.uid == UID
		episodeBase.series != null
		episodeBase.season != null
		episodeBase.title == TITLE
		episodeBase.titleGerman == TITLE_GERMAN
		episodeBase.titleItalian == TITLE_ITALIAN
		episodeBase.titleJapanese == TITLE_JAPANESE
		episodeBase.seasonNumber == SEASON_NUMBER
		episodeBase.episodeNumber == EPISODE_NUMBER
		episodeBase.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
		episodeBase.featureLength == FEATURE_LENGTH
		episodeBase.stardateFrom == STARDATE_FROM
		episodeBase.stardateTo == STARDATE_TO
		episodeBase.yearFrom == YEAR_FROM
		episodeBase.yearTo == YEAR_TO
		episodeBase.usAirDate == US_AIR_DATE
		episodeBase.finalScriptDate == FINAL_SCRIPT_DATE
	}

}
