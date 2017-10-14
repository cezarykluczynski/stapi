package com.cezarykluczynski.stapi.server.animal.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBase
import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.cezarykluczynski.stapi.server.animal.dto.AnimalRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AnimalBaseRestMapperTest extends AbstractAnimalMapperTest {

	private AnimalBaseRestMapper animalBaseRestMapper

	void setup() {
		animalBaseRestMapper = Mappers.getMapper(AnimalBaseRestMapper)
	}

	void "maps AnimalRestBeanParams to AnimalRequestDTO"() {
		given:
		AnimalRestBeanParams animalRestBeanParams = new AnimalRestBeanParams(
				uid: UID,
				name: NAME,
				earthAnimal: EARTH_ANIMAL,
				earthInsect: EARTH_INSECT,
				avian: AVIAN,
				canine: CANINE,
				feline: FELINE)

		when:
		AnimalRequestDTO animalRequestDTO = animalBaseRestMapper.mapBase animalRestBeanParams

		then:
		animalRequestDTO.uid == UID
		animalRequestDTO.name == NAME
		animalRequestDTO.earthAnimal == EARTH_ANIMAL
		animalRequestDTO.earthInsect == EARTH_INSECT
		animalRequestDTO.avian == AVIAN
		animalRequestDTO.canine == CANINE
		animalRequestDTO.feline == FELINE
	}

	void "maps DB entity to base REST entity"() {
		given:
		Animal animal = createAnimal()

		when:
		AnimalBase animalBase = animalBaseRestMapper.mapBase(Lists.newArrayList(animal))[0]

		then:
		animalBase.uid == UID
		animalBase.name == NAME
		animalBase.earthAnimal == EARTH_ANIMAL
		animalBase.earthInsect == EARTH_INSECT
		animalBase.avian == AVIAN
		animalBase.canine == CANINE
		animalBase.feline == FELINE
	}

}
