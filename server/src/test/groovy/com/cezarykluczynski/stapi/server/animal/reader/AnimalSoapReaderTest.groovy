package com.cezarykluczynski.stapi.server.animal.reader

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBase
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFull
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseSoapMapper
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullSoapMapper
import com.cezarykluczynski.stapi.server.animal.query.AnimalSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class AnimalSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private AnimalSoapQuery animalSoapQueryBuilderMock

	private AnimalBaseSoapMapper animalBaseSoapMapperMock

	private AnimalFullSoapMapper animalFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private AnimalSoapReader animalSoapReader

	void setup() {
		animalSoapQueryBuilderMock = Mock()
		animalBaseSoapMapperMock = Mock()
		animalFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		animalSoapReader = new AnimalSoapReader(animalSoapQueryBuilderMock, animalBaseSoapMapperMock, animalFullSoapMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Animal> animalList = Lists.newArrayList()
		Page<Animal> animalPage = Mock()
		List<AnimalBase> soapAnimalList = Lists.newArrayList(new AnimalBase(uid: UID))
		AnimalBaseRequest animalBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		AnimalBaseResponse animalResponse = animalSoapReader.readBase(animalBaseRequest)

		then:
		1 * animalSoapQueryBuilderMock.query(animalBaseRequest) >> animalPage
		1 * animalPage.content >> animalList
		1 * pageMapperMock.fromPageToSoapResponsePage(animalPage) >> responsePage
		1 * animalBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * animalBaseSoapMapperMock.mapBase(animalList) >> soapAnimalList
		0 * _
		animalResponse.animals[0].uid == UID
		animalResponse.page == responsePage
		animalResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		AnimalFull animalFull = new AnimalFull(uid: UID)
		Animal animal = Mock()
		Page<Animal> animalPage = Mock()
		AnimalFullRequest animalFullRequest = new AnimalFullRequest(uid: UID)

		when:
		AnimalFullResponse animalFullResponse = animalSoapReader.readFull(animalFullRequest)

		then:
		1 * animalSoapQueryBuilderMock.query(animalFullRequest) >> animalPage
		1 * animalPage.content >> Lists.newArrayList(animal)
		1 * animalFullSoapMapperMock.mapFull(animal) >> animalFull
		0 * _
		animalFullResponse.animal.uid == UID
	}

	void "requires UID in full request"() {
		given:
		AnimalFullRequest animalFullRequest = Mock()

		when:
		animalSoapReader.readFull(animalFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
