package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.AnimalApi
import com.cezarykluczynski.stapi.client.rest.model.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.AnimalFullResponse
import com.cezarykluczynski.stapi.client.rest.model.AnimalSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractAnimalTest

class AnimalTest extends AbstractAnimalTest {

	private AnimalApi animalApiMock

	private Animal animal

	void setup() {
		animalApiMock = Mock()
		animal = new Animal(animalApiMock)
	}

	void "gets single entity"() {
		given:
		AnimalFullResponse animalFullResponse = Mock()

		when:
		AnimalFullResponse animalFullResponseOutput = animal.get(UID)

		then:
		1 * animalApiMock.v1GetAnimal(UID) >> animalFullResponse
		0 * _
		animalFullResponse == animalFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		AnimalBaseResponse animalBaseResponse = Mock()
		AnimalSearchCriteria animalSearchCriteria = new AnimalSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				earthAnimal: EARTH_ANIMAL,
				earthInsect: EARTH_INSECT,
				avian: AVIAN,
				canine: CANINE,
				feline: FELINE)
		animalSearchCriteria.sort = SORT

		when:
		AnimalBaseResponse animalBaseResponseOutput = animal.search(animalSearchCriteria)

		then:
		1 * animalApiMock.v1SearchAnimals(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EARTH_ANIMAL, EARTH_INSECT, AVIAN, CANINE,
				FELINE) >> animalBaseResponse
		0 * _
		animalBaseResponse == animalBaseResponseOutput
	}

}
