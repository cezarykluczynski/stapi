package com.cezarykluczynski.stapi.etl.animal.creation.processor

import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.cezarykluczynski.stapi.model.animal.repository.AnimalRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class AnimalWriterTest extends Specification {

	private AnimalRepository animalRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private AnimalWriter animalWriterMock

	void setup() {
		animalRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		animalWriterMock = new AnimalWriter(animalRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Animal animal = new Animal()
		List<Animal> animalList = Lists.newArrayList(animal)

		when:
		animalWriterMock.write(animalList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Animal) >> { args ->
			assert args[0][0] == animal
			animalList
		}
		1 * animalRepositoryMock.save(animalList)
		0 * _
	}

}
