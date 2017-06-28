package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBase
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class EpisodeBaseSoapMapperTest extends AbstractEpisodeMapperTest {

	private EpisodeBaseSoapMapper episodeBaseSoapMapper

	void setup() {
		episodeBaseSoapMapper = Mappers.getMapper(EpisodeBaseSoapMapper)
	}

	void "maps SOAP EpisodeBaseRequest to EpisodeRequestDTO"() {
		given:
		EpisodeBaseRequest episodeBaseRequest = new EpisodeBaseRequest(
				title: TITLE,
				seasonNumber: new IntegerRange(
						from: SEASON_NUMBER_FROM,
						to: SEASON_NUMBER_TO,
				),
				episodeNumber: new IntegerRange(
						from: EPISODE_NUMBER_FROM,
						to: EPISODE_NUMBER_TO,
				),
				productionSerialNumber: PRODUCTION_SERIAL_NUMBER,
				featureLength: FEATURE_LENGTH,
				stardate: new FloatRange(
						from: STARDATE_FROM,
						to: STARDATE_TO
				),
				year: new IntegerRange(
						from: YEAR_FROM,
						to: YEAR_TO
				),
				usAirDate: new DateRange(
						from: US_AIR_DATE_FROM_XML,
						to: US_AIR_DATE_TO_XML
				),
				finalScriptDate: new DateRange(
						from: FINAL_SCRIPT_DATE_FROM_XML,
						to: FINAL_SCRIPT_DATE_TO_XML
				)
		)

		when:
		EpisodeRequestDTO episodeRequestDTO = episodeBaseSoapMapper.mapBase episodeBaseRequest

		then:
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

	void "maps DB entity to base SOAP entity"() {
		given:
		Episode episode = createEpisode()

		when:
		EpisodeBase episodeBase = episodeBaseSoapMapper.mapBase(Lists.newArrayList(episode))[0]

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
		episodeBase.yearFrom.toInteger() == YEAR_FROM
		episodeBase.yearTo.toInteger() == YEAR_TO
		episodeBase.usAirDate == US_AIR_DATE_XML
		episodeBase.finalScriptDate == FINAL_SCRIPT_DATE_XML
	}

}
