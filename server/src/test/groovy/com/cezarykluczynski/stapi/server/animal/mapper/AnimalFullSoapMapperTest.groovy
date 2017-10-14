package com.cezarykluczynski.stapi.server.animal.mapper

import com.cezarykluczynski.stapi.client.v1.soap.AnimalFull
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest
import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import org.mapstruct.factory.Mappers

class AnimalFullSoapMapperTest extends AbstractAnimalMapperTest {

	private AnimalFullSoapMapper animalFullSoapMapper

	void setup() {
		animalFullSoapMapper = Mappers.getMapper(AnimalFullSoapMapper)
	}

	void "maps SOAP AnimalFullRequest to AnimalBaseRequestDTO"() {
		given:
		AnimalFullRequest animalFullRequest = new AnimalFullRequest(uid: UID)

		when:
		AnimalRequestDTO animalRequestDTO = animalFullSoapMapper.mapFull animalFullRequest

		then:
		animalRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Animal animal = createAnimal()

		when:
		AnimalFull animalFull = animalFullSoapMapper.mapFull(animal)

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
