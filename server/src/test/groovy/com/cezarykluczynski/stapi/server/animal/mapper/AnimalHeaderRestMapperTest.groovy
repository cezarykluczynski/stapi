package com.cezarykluczynski.stapi.server.animal.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalHeader
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AnimalHeaderRestMapperTest extends AbstractAnimalMapperTest {

	private AnimalHeaderRestMapper animalHeaderRestMapper

	void setup() {
		animalHeaderRestMapper = Mappers.getMapper(AnimalHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Animal animal = new Animal(
				uid: UID,
				name: NAME)

		when:
		AnimalHeader animalHeader = animalHeaderRestMapper.map(Lists.newArrayList(animal))[0]

		then:
		animalHeader.uid == UID
		animalHeader.name == NAME
	}

}
