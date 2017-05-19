package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBase
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MovieBaseRestMapperTest extends AbstractMovieMapperTest {

	private MovieBaseRestMapper movieBaseRestMapper

	void setup() {
		movieBaseRestMapper = Mappers.getMapper(MovieBaseRestMapper)
	}

	void "maps MovieRestBeanParams to MovieRequestDTO"() {
		given:
		MovieRestBeanParams movieRestBeanParams = new MovieRestBeanParams(
				uid: UID,
				title: TITLE,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				usReleaseDateFrom: US_RELEASE_DATE_FROM,
				usReleaseDateTo: US_RELEASE_DATE_TO)

		when:
		MovieRequestDTO movieRequestDTO = movieBaseRestMapper.mapBase movieRestBeanParams

		then:
		movieRequestDTO.uid == UID
		movieRequestDTO.title == TITLE
		movieRequestDTO.stardateFrom == STARDATE_FROM
		movieRequestDTO.stardateTo == STARDATE_TO
		movieRequestDTO.yearFrom == YEAR_FROM
		movieRequestDTO.yearTo == YEAR_TO
		movieRequestDTO.usReleaseDateFrom == US_RELEASE_DATE_FROM
		movieRequestDTO.usReleaseDateTo == US_RELEASE_DATE_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		Movie movie = createMovie()

		when:
		MovieBase movieBase = movieBaseRestMapper.mapBase(Lists.newArrayList(movie))[0]

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
		movieBase.stardateFrom == STARDATE_FROM
		movieBase.stardateTo == STARDATE_TO
		movieBase.yearFrom == YEAR_FROM
		movieBase.yearTo == YEAR_TO
		movieBase.usReleaseDate == US_RELEASE_DATE
	}

}
