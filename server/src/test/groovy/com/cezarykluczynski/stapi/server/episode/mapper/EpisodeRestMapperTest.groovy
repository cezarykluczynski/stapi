package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Episode as RESTEpisode
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesHeader
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.entity.Episode as DBEpisode
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class EpisodeRestMapperTest extends AbstractEpisodeMapperTest {

	private EpisodeRestMapper episodeRestMapper

	def setup() {
		episodeRestMapper = Mappers.getMapper(EpisodeRestMapper)
	}

	def "maps EpisodeRestBeanParams to EpisodeRequestDTO"() {
		given:
		EpisodeRestBeanParams episodeRestBeanParams = new EpisodeRestBeanParams(
				guid: GUID,
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
		EpisodeRequestDTO episodeRequestDTO = episodeRestMapper.map episodeRestBeanParams

		then:
		episodeRequestDTO.guid == GUID
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

	def "maps DB entity to REST entity"() {
		given:
		DBEpisode dBEpisode = createEpisode()

		when:
		RESTEpisode restEpisode = episodeRestMapper.map(Lists.newArrayList(dBEpisode))[0]

		then:
		restEpisode.guid == GUID
		restEpisode.series instanceof SeriesHeader
		restEpisode.title == TITLE
		restEpisode.seasonNumber == SEASON_NUMBER
		restEpisode.episodeNumber == EPISODE_NUMBER
		restEpisode.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
		restEpisode.featureLength == FEATURE_LENGTH
		restEpisode.stardate == STARDATE
		restEpisode.year == YEAR
		restEpisode.usAirDate == US_AIR_DATE
		restEpisode.finalScriptDate == FINAL_SCRIPT_DATE
		restEpisode.writerHeaders.size() == dBEpisode.writers.size()
		restEpisode.teleplayAuthorHeaders.size() == dBEpisode.teleplayAuthors.size()
		restEpisode.storyAuthorHeaders.size() == dBEpisode.storyAuthors.size()
		restEpisode.directorHeaders.size() == dBEpisode.directors.size()
		restEpisode.performerHeaders.size() == dBEpisode.performers.size()
		restEpisode.stuntPerformerHeaders.size() == dBEpisode.stuntPerformers.size()
		restEpisode.standInPerformerHeaders.size() == dBEpisode.standInPerformers.size()
		restEpisode.characterHeaders.size() == dBEpisode.characters.size()
	}

}
