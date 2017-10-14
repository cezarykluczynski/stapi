package com.cezarykluczynski.stapi.server.animal.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBase
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFull
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.cezarykluczynski.stapi.server.animal.dto.AnimalRestBeanParams
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseRestMapper
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullRestMapper
import com.cezarykluczynski.stapi.server.animal.query.AnimalRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class AnimalRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private AnimalRestQuery animalRestQueryBuilderMock

	private AnimalBaseRestMapper animalBaseRestMapperMock

	private AnimalFullRestMapper animalFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private AnimalRestReader animalRestReader

	void setup() {
		animalRestQueryBuilderMock = Mock()
		animalBaseRestMapperMock = Mock()
		animalFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		animalRestReader = new AnimalRestReader(animalRestQueryBuilderMock, animalBaseRestMapperMock, animalFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		AnimalBase animalBase = Mock()
		Animal animal = Mock()
		AnimalRestBeanParams animalRestBeanParams = Mock()
		List<AnimalBase> restAnimalList = Lists.newArrayList(animalBase)
		List<Animal> animalList = Lists.newArrayList(animal)
		Page<Animal> animalPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		AnimalBaseResponse animalResponseOutput = animalRestReader.readBase(animalRestBeanParams)

		then:
		1 * animalRestQueryBuilderMock.query(animalRestBeanParams) >> animalPage
		1 * pageMapperMock.fromPageToRestResponsePage(animalPage) >> responsePage
		1 * animalRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * animalPage.content >> animalList
		1 * animalBaseRestMapperMock.mapBase(animalList) >> restAnimalList
		0 * _
		animalResponseOutput.animals == restAnimalList
		animalResponseOutput.page == responsePage
		animalResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		AnimalFull animalFull = Mock()
		Animal animal = Mock()
		List<Animal> animalList = Lists.newArrayList(animal)
		Page<Animal> animalPage = Mock()

		when:
		AnimalFullResponse animalResponseOutput = animalRestReader.readFull(UID)

		then:
		1 * animalRestQueryBuilderMock.query(_ as AnimalRestBeanParams) >> { AnimalRestBeanParams animalRestBeanParams ->
			assert animalRestBeanParams.uid == UID
			animalPage
		}
		1 * animalPage.content >> animalList
		1 * animalFullRestMapperMock.mapFull(animal) >> animalFull
		0 * _
		animalResponseOutput.animal == animalFull
	}

	void "requires UID in full request"() {
		when:
		animalRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
