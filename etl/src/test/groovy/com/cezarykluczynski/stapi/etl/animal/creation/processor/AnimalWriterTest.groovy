package com.cezarykluczynski.stapi.etl.animal.creation.processor

import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.cezarykluczynski.stapi.model.animal.repository.AnimalRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class AnimalWriterTest extends Specification {

	private AnimalRepository animalRepositoryMock

	private AnimalWriter animalWriterMock

	void setup() {
		animalRepositoryMock = Mock()
		animalWriterMock = new AnimalWriter(animalRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Animal animal = new Animal()
		List<Animal> animalList = Lists.newArrayList(animal)

		when:
		animalWriterMock.write(new Chunk(animalList))

		then:
		1 * animalRepositoryMock.saveAll(animalList)
		0 * _
	}

}
