package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.AnimalApi
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFullResponse
import com.cezarykluczynski.stapi.util.AbstractAnimalTest

class AnimalTest extends AbstractAnimalTest {

	private AnimalApi animalApiMock

	private Animal animal

	void setup() {
		animalApiMock = Mock()
		animal = new Animal(animalApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		AnimalFullResponse animalFullResponse = Mock()

		when:
		AnimalFullResponse animalFullResponseOutput = animal.get(UID)

		then:
		1 * animalApiMock.animalGet(UID, API_KEY) >> animalFullResponse
		0 * _
		animalFullResponse == animalFullResponseOutput
	}

	void "searches entities"() {
		given:
		AnimalBaseResponse animalBaseResponse = Mock()

		when:
		AnimalBaseResponse animalBaseResponseOutput = animal.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, EARTH_ANIMAL, EARTH_INSECT, AVIAN, CANINE,
				FELINE)

		then:
		1 * animalApiMock.animalSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, EARTH_ANIMAL, EARTH_INSECT, AVIAN, CANINE, FELINE) >>
				animalBaseResponse
		0 * _
		animalBaseResponse == animalBaseResponseOutput
	}

}
