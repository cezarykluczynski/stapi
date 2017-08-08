package com.cezarykluczynski.stapi.server.genre.mapper

import com.cezarykluczynski.stapi.client.v1.soap.Genre as SoapGenre
import com.cezarykluczynski.stapi.model.genre.entity.Genre
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class GenreSoapMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private GenreSoapMapper genreSoapMapper

	void setup() {
		genreSoapMapper = Mappers.getMapper(GenreSoapMapper)
	}

	void "maps db entity to SOAP entity"() {
		given:
		Genre genre = new Genre(
				uid: UID,
				name: NAME)

		when:
		SoapGenre soapGenre = genreSoapMapper.map(genre)

		then:
		soapGenre.uid == UID
		soapGenre.name == NAME
	}

}
