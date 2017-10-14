package com.cezarykluczynski.stapi.model.animal.repository

import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.cezarykluczynski.stapi.model.animal.entity.Animal_
import com.cezarykluczynski.stapi.model.animal.query.AnimalQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractAnimalTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class AnimalRepositoryImplTest extends AbstractAnimalTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private AnimalQueryBuilderFactory animalQueryBuilderFactory

	private AnimalRepositoryImpl animalRepositoryImpl

	private QueryBuilder<Animal> animalQueryBuilder

	private Pageable pageable

	private AnimalRequestDTO animalRequestDTO

	private Animal animal

	private Page page

	void setup() {
		animalQueryBuilderFactory = Mock()
		animalRepositoryImpl = new AnimalRepositoryImpl(animalQueryBuilderFactory)
		animalQueryBuilder = Mock()
		pageable = Mock()
		animalRequestDTO = Mock()
		page = Mock()
		animal = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = animalRepositoryImpl.findMatching(animalRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * animalQueryBuilderFactory.createQueryBuilder(pageable) >> animalQueryBuilder

		then: 'uid criteria is set'
		1 * animalRequestDTO.uid >> UID
		1 * animalQueryBuilder.equal(Animal_.uid, UID)

		then: 'string criteria are set'
		1 * animalRequestDTO.name >> NAME
		1 * animalQueryBuilder.like(Animal_.name, NAME)

		then: 'boolean criteria are set'
		1 * animalRequestDTO.earthAnimal >> EARTH_ANIMAL
		1 * animalQueryBuilder.equal(Animal_.earthAnimal, EARTH_ANIMAL)
		1 * animalRequestDTO.earthInsect >> EARTH_INSECT
		1 * animalQueryBuilder.equal(Animal_.earthInsect, EARTH_INSECT)
		1 * animalRequestDTO.avian >> AVIAN
		1 * animalQueryBuilder.equal(Animal_.avian, AVIAN)
		1 * animalRequestDTO.canine >> CANINE
		1 * animalQueryBuilder.equal(Animal_.canine, CANINE)
		1 * animalRequestDTO.feline >> FELINE
		1 * animalQueryBuilder.equal(Animal_.feline, FELINE)

		then: 'sort is set'
		1 * animalRequestDTO.sort >> SORT
		1 * animalQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * animalQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
