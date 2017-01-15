package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.Episode as SOAPEpisode
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.SeriesHeader
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.entity.Episode as DBEpisode
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class EpisodeSoapMapperTest extends AbstractEpisodeMapperTest {

	private EpisodeSoapMapper episodeSoapMapper

	def setup() {
		episodeSoapMapper = Mappers.getMapper(EpisodeSoapMapper)
	}

	def "maps SOAP EpisodeRequest to EpisodeRequestDTO"() {
		given:
		EpisodeRequest episodeRequest = new EpisodeRequest(
				guid: GUID,
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
		EpisodeRequestDTO episodeRequestDTO = episodeSoapMapper.map episodeRequest

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

	def "maps DB entity to SOAP entity"() {
		given:
		DBEpisode dBEpisode = createEpisode()

		when:
		SOAPEpisode soapEpisode = episodeSoapMapper.map(Lists.newArrayList(dBEpisode))[0]

		then:
		soapEpisode.guid == GUID
		soapEpisode.series instanceof SeriesHeader
		soapEpisode.title == TITLE
		soapEpisode.titleGerman == TITLE_GERMAN
		soapEpisode.titleItalian == TITLE_ITALIAN
		soapEpisode.titleJapanese == TITLE_JAPANESE
		soapEpisode.seasonNumber == SEASON_NUMBER
		soapEpisode.episodeNumber == EPISODE_NUMBER
		soapEpisode.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
		soapEpisode.featureLength == FEATURE_LENGTH
		soapEpisode.stardateFrom == STARDATE_FROM
		soapEpisode.stardateTo == STARDATE_TO
		soapEpisode.yearFrom.toInteger() == YEAR_FROM
		soapEpisode.yearTo.toInteger() == YEAR_TO
		soapEpisode.usAirDate == US_AIR_DATE_XML
		soapEpisode.finalScriptDate == FINAL_SCRIPT_DATE_XML
		soapEpisode.writerHeaders.size() == dBEpisode.writers.size()
		soapEpisode.teleplayAuthorHeaders.size() == dBEpisode.teleplayAuthors.size()
		soapEpisode.storyAuthorHeaders.size() == dBEpisode.storyAuthors.size()
		soapEpisode.directorHeaders.size() == dBEpisode.directors.size()
		soapEpisode.performerHeaders.size() == dBEpisode.performers.size()
		soapEpisode.stuntPerformerHeaders.size() == dBEpisode.stuntPerformers.size()
		soapEpisode.standInPerformerHeaders.size() == dBEpisode.standInPerformers.size()
		soapEpisode.characterHeaders.size() == dBEpisode.characters.size()
	}



}
