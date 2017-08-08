package com.cezarykluczynski.stapi.server.genre.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Genre as RestGenre
import com.cezarykluczynski.stapi.model.genre.entity.Genre
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class GenreRestMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private GenreRestMapper genreRestMapper

	void setup() {
		genreRestMapper = Mappers.getMapper(GenreRestMapper)
	}

	void "maps db entity to REST entity"() {
		given:
		Genre genre = new Genre(
				uid: UID,
				name: NAME)

		when:
		RestGenre restGenre = genreRestMapper.map(genre)

		then:
		restGenre.uid == UID
		restGenre.name == NAME
	}

}
