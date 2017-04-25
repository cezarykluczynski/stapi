package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MovieHeader
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MovieHeaderSoapMapperTest extends AbstractMovieMapperTest {

	private MovieHeaderSoapMapper movieHeaderSoapMapper

	void setup() {
		movieHeaderSoapMapper = Mappers.getMapper(MovieHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Movie movie = new Movie(
				uid: UID,
				title: TITLE)

		when:
		MovieHeader movieHeader = movieHeaderSoapMapper.map(Lists.newArrayList(movie))[0]

		then:
		movieHeader.uid == UID
		movieHeader.title == TITLE
	}

}
