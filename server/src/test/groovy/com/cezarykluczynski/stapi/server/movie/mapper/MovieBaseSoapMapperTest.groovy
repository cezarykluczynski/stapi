package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.MovieBase as MovieBase
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MovieBaseSoapMapperTest extends AbstractMovieMapperTest {

	private MovieBaseSoapMapper movieBaseSoapMapper

	void setup() {
		movieBaseSoapMapper = Mappers.getMapper(MovieBaseSoapMapper)
	}

	void "maps SOAP MovieRequest to MovieRequestDTO"() {
		given:
		MovieBaseRequest movieBaseRequest = new MovieBaseRequest(
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
		MovieRequestDTO movieRequestDTO = movieBaseSoapMapper.mapBase movieBaseRequest

		then:
		movieRequestDTO.title == TITLE
		movieRequestDTO.stardateFrom == STARDATE_FROM
		movieRequestDTO.stardateTo == STARDATE_TO
		movieRequestDTO.yearFrom == YEAR_FROM
		movieRequestDTO.yearTo == YEAR_TO
		movieRequestDTO.usReleaseDateFrom == US_RELEASE_DATE_FROM
		movieRequestDTO.usReleaseDateTo == US_RELEASE_DATE_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Movie movie = createMovie()

		when:
		MovieBase movieBase = movieBaseSoapMapper.mapBase(Lists.newArrayList(movie))[0]

		then:
		movieBase.uid == UID
		movieBase.mainDirector != null
		movieBase.title == TITLE
		movieBase.titleBulgarian == TITLE_BULGARIAN
		movieBase.titleCatalan == TITLE_CATALAN
		movieBase.titleChineseTraditional == TITLE_CHINESE_TRADITIONAL
		movieBase.titleGerman == TITLE_GERMAN
		movieBase.titleItalian == TITLE_ITALIAN
		movieBase.titleJapanese == TITLE_JAPANESE
		movieBase.titlePolish == TITLE_POLISH
		movieBase.titleRussian == TITLE_RUSSIAN
		movieBase.titleSerbian == TITLE_SERBIAN
		movieBase.titleSpanish == TITLE_SPANISH
		movieBase.usReleaseDate == US_RELEASE_DATE_XML
		movieBase.stardateFrom == STARDATE_FROM
		movieBase.stardateTo == STARDATE_TO
		movieBase.yearFrom.toInteger() == YEAR_FROM
		movieBase.yearTo.toInteger() == YEAR_TO
	}

}
