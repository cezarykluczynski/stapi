package com.cezarykluczynski.stapi.server.animal.mapper

import com.cezarykluczynski.stapi.client.v1.soap.AnimalHeader
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AnimalHeaderSoapMapperTest extends AbstractAnimalMapperTest {

	private AnimalHeaderSoapMapper animalHeaderSoapMapper

	void setup() {
		animalHeaderSoapMapper = Mappers.getMapper(AnimalHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Animal animal = new Animal(
				uid: UID,
				name: NAME)

		when:
		AnimalHeader animalHeader = animalHeaderSoapMapper.map(Lists.newArrayList(animal))[0]

		then:
		animalHeader.uid == UID
		animalHeader.name == NAME
	}

}
