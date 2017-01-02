package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieHeader
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MovieHeaderRestMapperTest extends AbstractMovieMapperTest {

	private MovieHeaderRestMapper movieHeaderRestMapper

	def setup() {
		movieHeaderRestMapper = Mappers.getMapper(MovieHeaderRestMapper)
	}

	def "maps DB entity to REST header"() {
		given:
		Movie movie = new Movie(
				guid: GUID,
				title: TITLE)

		when:
		MovieHeader movieHeader = movieHeaderRestMapper.map(Lists.newArrayList(movie))[0]

		then:
		movieHeader.guid == GUID
		movieHeader.title == TITLE
	}

}
