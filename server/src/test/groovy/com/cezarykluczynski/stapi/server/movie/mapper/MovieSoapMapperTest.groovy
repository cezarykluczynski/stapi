package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.Movie as SOAPMovie
import com.cezarykluczynski.stapi.client.v1.soap.MovieRequest
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie as DBMovie
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MovieSoapMapperTest extends AbstractMovieMapperTest {

	private MovieSoapMapper movieSoapMapper

	def setup() {
		movieSoapMapper = Mappers.getMapper(MovieSoapMapper)
	}

	def "maps SOAP MovieRequest to MovieRequestDTO"() {
		given:
		MovieRequest movieRequest = new MovieRequest(
				guid: GUID,
				title: TITLE,
				stardate: new FloatRange(
						from: STARDATE_FROM,
						to: STARDATE_TO,
				),
				year: new IntegerRange(
						from: YEAR_FROM,
						to: YEAR_TO,
				),
				usReleaseDate: new DateRange(
						from: US_RELEASE_DATE_FROM_XML,
						to: US_RELEASE_DATE_TO_XML
				))

		when:
		MovieRequestDTO movieRequestDTO = movieSoapMapper.map movieRequest

		then:
		movieRequestDTO.guid == GUID
		movieRequestDTO.title == TITLE
		movieRequestDTO.stardateFrom == STARDATE_FROM
		movieRequestDTO.stardateTo == STARDATE_TO
		movieRequestDTO.yearFrom == YEAR_FROM
		movieRequestDTO.yearTo == YEAR_TO
		movieRequestDTO.usReleaseDateFrom == US_RELEASE_DATE_FROM
		movieRequestDTO.usReleaseDateTo == US_RELEASE_DATE_TO
	}

	def "maps DB entity to SOAP entity"() {
		given:
		DBMovie dBMovie = createMovie()

		when:
		SOAPMovie soapMovie = movieSoapMapper.map(Lists.newArrayList(dBMovie))[0]

		then:
		soapMovie.guid == GUID
		soapMovie.mainDirector != null
		soapMovie.title == TITLE
		soapMovie.titleBulgarian == TITLE_BULGARIAN
		soapMovie.titleCatalan == TITLE_CATALAN
		soapMovie.titleChineseTraditional == TITLE_CHINESE_TRADITIONAL
		soapMovie.titleGerman == TITLE_GERMAN
		soapMovie.titleItalian == TITLE_ITALIAN
		soapMovie.titleJapanese == TITLE_JAPANESE
		soapMovie.titlePolish == TITLE_POLISH
		soapMovie.titleRussian == TITLE_RUSSIAN
		soapMovie.titleSerbian == TITLE_SERBIAN
		soapMovie.titleSpanish == TITLE_SPANISH
		soapMovie.usReleaseDate == US_RELEASE_DATE_XML
		soapMovie.stardateFrom == STARDATE_FROM
		soapMovie.stardateTo == STARDATE_TO
		soapMovie.yearFrom.toInteger() == YEAR_FROM
		soapMovie.yearTo.toInteger() == YEAR_TO
		soapMovie.writerHeaders.size() == dBMovie.writers.size()
		soapMovie.screenplayAuthorHeaders.size() == dBMovie.screenplayAuthors.size()
		soapMovie.storyAuthorHeaders.size() == dBMovie.storyAuthors.size()
		soapMovie.directorHeaders.size() == dBMovie.directors.size()
		soapMovie.producerHeaders.size() == dBMovie.producers.size()
		soapMovie.staffHeaders.size() == dBMovie.staff.size()
		soapMovie.performerHeaders.size() == dBMovie.performers.size()
		soapMovie.stuntPerformerHeaders.size() == dBMovie.stuntPerformers.size()
		soapMovie.standInPerformerHeaders.size() == dBMovie.standInPerformers.size()
		soapMovie.characterHeaders.size() == dBMovie.characters.size()
	}

}
