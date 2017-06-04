package com.cezarykluczynski.stapi.server.species.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBase
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFull
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullResponse
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseSoapMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullSoapMapper
import com.cezarykluczynski.stapi.server.species.query.SpeciesSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpeciesSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private SpeciesSoapQuery speciesSoapQueryBuilderMock

	private SpeciesBaseSoapMapper speciesBaseSoapMapperMock

	private SpeciesFullSoapMapper speciesFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SpeciesSoapReader speciesSoapReader

	void setup() {
		speciesSoapQueryBuilderMock = Mock()
		speciesBaseSoapMapperMock = Mock()
		speciesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		speciesSoapReader = new SpeciesSoapReader(speciesSoapQueryBuilderMock, speciesBaseSoapMapperMock, speciesFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Species> speciesList = Lists.newArrayList()
		Page<Species> speciesPage = Mock()
		List<SpeciesBase> soapSpeciesList = Lists.newArrayList(new SpeciesBase(uid: UID))
		SpeciesBaseRequest speciesBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		SpeciesBaseResponse speciesResponse = speciesSoapReader.readBase(speciesBaseRequest)

		then:
		1 * speciesSoapQueryBuilderMock.query(speciesBaseRequest) >> speciesPage
		1 * speciesPage.content >> speciesList
		1 * pageMapperMock.fromPageToSoapResponsePage(speciesPage) >> responsePage
		1 * speciesBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * speciesBaseSoapMapperMock.mapBase(speciesList) >> soapSpeciesList
		0 * _
		speciesResponse.species[0].uid == UID
		speciesResponse.page == responsePage
		speciesResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpeciesFull speciesFull = new SpeciesFull(uid: UID)
		Species species = Mock()
		Page<Species> speciesPage = Mock()
		SpeciesFullRequest speciesFullRequest = new SpeciesFullRequest(uid: UID)

		when:
		SpeciesFullResponse speciesFullResponse = speciesSoapReader.readFull(speciesFullRequest)

		then:
		1 * speciesSoapQueryBuilderMock.query(speciesFullRequest) >> speciesPage
		1 * speciesPage.content >> Lists.newArrayList(species)
		1 * speciesFullSoapMapperMock.mapFull(species) >> speciesFull
		0 * _
		speciesFullResponse.species.uid == UID
	}

	void "requires UID in full request"() {
		given:
		SpeciesFullRequest speciesFullRequest = Mock()

		when:
		speciesSoapReader.readFull(speciesFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
