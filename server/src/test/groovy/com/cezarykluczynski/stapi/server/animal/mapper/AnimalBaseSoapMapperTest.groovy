package com.cezarykluczynski.stapi.server.animal.mapper

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBase
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest
import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AnimalBaseSoapMapperTest extends AbstractAnimalMapperTest {

	private AnimalBaseSoapMapper animalBaseSoapMapper

	void setup() {
		animalBaseSoapMapper = Mappers.getMapper(AnimalBaseSoapMapper)
	}

	void "maps SOAP AnimalBaseRequest to AnimalRequestDTO"() {
		given:
		AnimalBaseRequest animalBaseRequest = new AnimalBaseRequest(
				name: NAME,
				earthAnimal: EARTH_ANIMAL,
				earthInsect: EARTH_INSECT,
				avian: AVIAN,
				canine: CANINE,
				feline: FELINE)

		when:
		AnimalRequestDTO animalRequestDTO = animalBaseSoapMapper.mapBase animalBaseRequest

		then:
		animalRequestDTO.name == NAME
		animalRequestDTO.earthAnimal == EARTH_ANIMAL
		animalRequestDTO.earthInsect == EARTH_INSECT
		animalRequestDTO.avian == AVIAN
		animalRequestDTO.canine == CANINE
		animalRequestDTO.feline == FELINE
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Animal animal = createAnimal()

		when:
		AnimalBase animalBase = animalBaseSoapMapper.mapBase(Lists.newArrayList(animal))[0]

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
