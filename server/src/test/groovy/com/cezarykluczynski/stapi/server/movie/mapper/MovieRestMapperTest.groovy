package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Movie as RESTMovie
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie as DBMovie
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MovieRestMapperTest extends AbstractMovieMapperTest {

	private MovieRestMapper movieRestMapper

	void setup() {
		movieRestMapper = Mappers.getMapper(MovieRestMapper)
	}

	void "maps MovieRestBeanParams to MovieRequestDTO"() {
		given:
		MovieRestBeanParams movieRestBeanParams = new MovieRestBeanParams(
				guid: GUID,
				title: TITLE,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				usReleaseDateFrom: US_RELEASE_DATE_FROM,
				usReleaseDateTo: US_RELEASE_DATE_TO)

		when:
		MovieRequestDTO movieRequestDTO = movieRestMapper.map movieRestBeanParams

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

	void "maps DB entity to REST entity"() {
		given:
		DBMovie dBMovie = createMovie()

		when:
		RESTMovie restMovie = movieRestMapper.map(Lists.newArrayList(dBMovie))[0]

		then:
		restMovie.guid == GUID
		restMovie.mainDirector != null
		restMovie.title == TITLE
		restMovie.titleBulgarian == TITLE_BULGARIAN
		restMovie.titleCatalan == TITLE_CATALAN
		restMovie.titleChineseTraditional == TITLE_CHINESE_TRADITIONAL
		restMovie.titleGerman == TITLE_GERMAN
		restMovie.titleItalian == TITLE_ITALIAN
		restMovie.titleJapanese == TITLE_JAPANESE
		restMovie.titlePolish == TITLE_POLISH
		restMovie.titleRussian == TITLE_RUSSIAN
		restMovie.titleSerbian == TITLE_SERBIAN
		restMovie.titleSpanish == TITLE_SPANISH
		restMovie.stardateFrom == STARDATE_FROM
		restMovie.stardateTo == STARDATE_TO
		restMovie.yearFrom == YEAR_FROM
		restMovie.yearTo == YEAR_TO
		restMovie.usReleaseDate == US_RELEASE_DATE
		restMovie.writerHeaders.size() == dBMovie.writers.size()
		restMovie.screenplayAuthorHeaders.size() == dBMovie.screenplayAuthors.size()
		restMovie.storyAuthorHeaders.size() == dBMovie.storyAuthors.size()
		restMovie.directorHeaders.size() == dBMovie.directors.size()
		restMovie.producerHeaders.size() == dBMovie.producers.size()
		restMovie.staffHeaders.size() == dBMovie.staff.size()
		restMovie.performerHeaders.size() == dBMovie.performers.size()
		restMovie.stuntPerformerHeaders.size() == dBMovie.stuntPerformers.size()
		restMovie.standInPerformerHeaders.size() == dBMovie.standInPerformers.size()
		restMovie.characterHeaders.size() == dBMovie.characters.size()
	}

}
