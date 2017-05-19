package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MovieFull
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import org.mapstruct.factory.Mappers

class MovieFullSoapMapperTest extends AbstractMovieMapperTest {

	private MovieFullSoapMapper movieFullSoapMapper

	void setup() {
		movieFullSoapMapper = Mappers.getMapper(MovieFullSoapMapper)
	}

	void "maps SOAP MovieFullRequest to MovieBaseRequestDTO"() {
		given:
		MovieFullRequest movieFullRequest = new MovieFullRequest(uid: UID)

		when:
		MovieRequestDTO movieRequestDTO = movieFullSoapMapper.mapFull movieFullRequest

		then:
		movieRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Movie movie = createMovie()

		when:
		MovieFull movieFull = movieFullSoapMapper.mapFull(movie)

		then:
		movieFull.uid == UID
		movieFull.mainDirector != null
		movieFull.title == TITLE
		movieFull.titleBulgarian == TITLE_BULGARIAN
		movieFull.titleCatalan == TITLE_CATALAN
		movieFull.titleChineseTraditional == TITLE_CHINESE_TRADITIONAL
		movieFull.titleGerman == TITLE_GERMAN
		movieFull.titleItalian == TITLE_ITALIAN
		movieFull.titleJapanese == TITLE_JAPANESE
		movieFull.titlePolish == TITLE_POLISH
		movieFull.titleRussian == TITLE_RUSSIAN
		movieFull.titleSerbian == TITLE_SERBIAN
		movieFull.titleSpanish == TITLE_SPANISH
		movieFull.usReleaseDate == US_RELEASE_DATE_XML
		movieFull.stardateFrom == STARDATE_FROM
		movieFull.stardateTo == STARDATE_TO
		movieFull.yearFrom.toInteger() == YEAR_FROM
		movieFull.yearTo.toInteger() == YEAR_TO
		movieFull.writers.size() == movie.writers.size()
		movieFull.screenplayAuthors.size() == movie.screenplayAuthors.size()
		movieFull.storyAuthors.size() == movie.storyAuthors.size()
		movieFull.directors.size() == movie.directors.size()
		movieFull.producers.size() == movie.producers.size()
		movieFull.staff.size() == movie.staff.size()
		movieFull.performers.size() == movie.performers.size()
		movieFull.stuntPerformers.size() == movie.stuntPerformers.size()
		movieFull.standInPerformers.size() == movie.standInPerformers.size()
		movieFull.characters.size() == movie.characters.size()
	}

}
