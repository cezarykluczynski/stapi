package com.cezarykluczynski.stapi.server.animal.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFull
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import org.mapstruct.factory.Mappers

class AnimalFullRestMapperTest extends AbstractAnimalMapperTest {

	private AnimalFullRestMapper animalFullRestMapper

	void setup() {
		animalFullRestMapper = Mappers.getMapper(AnimalFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Animal dBAnimal = createAnimal()

		when:
		AnimalFull animalFull = animalFullRestMapper.mapFull(dBAnimal)

		then:
		animalFull.uid == UID
		animalFull.name == NAME
		animalFull.earthAnimal == EARTH_ANIMAL
		animalFull.earthInsect == EARTH_INSECT
		animalFull.avian == AVIAN
		animalFull.canine == CANINE
		animalFull.feline == FELINE
	}

}
