package com.cezarykluczynski.stapi.server.species.reader

import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBase
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFull
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullResponse
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingGUIDException
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseSoapMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullSoapMapper
import com.cezarykluczynski.stapi.server.species.query.SpeciesSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpeciesSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private SpeciesSoapQuery speciesSoapQueryBuilderMock

	private SpeciesBaseSoapMapper speciesBaseSoapMapperMock

	private SpeciesFullSoapMapper speciesFullSoapMapperMock

	private PageMapper pageMapperMock

	private SpeciesSoapReader speciesSoapReader

	void setup() {
		speciesSoapQueryBuilderMock = Mock()
		speciesBaseSoapMapperMock = Mock()
		speciesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		speciesSoapReader = new SpeciesSoapReader(speciesSoapQueryBuilderMock, speciesBaseSoapMapperMock, speciesFullSoapMapperMock,
				pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Species> speciesList = Lists.newArrayList()
		Page<Species> speciesPage = Mock()
		List<SpeciesBase> soapSpeciesList = Lists.newArrayList(new SpeciesBase(guid: GUID))
		SpeciesBaseRequest speciesBaseRequest = Mock()
		ResponsePage responsePage = Mock()

		when:
		SpeciesBaseResponse speciesResponse = speciesSoapReader.readBase(speciesBaseRequest)

		then:
		1 * speciesSoapQueryBuilderMock.query(speciesBaseRequest) >> speciesPage
		1 * speciesPage.content >> speciesList
		1 * pageMapperMock.fromPageToSoapResponsePage(speciesPage) >> responsePage
		1 * speciesBaseSoapMapperMock.mapBase(speciesList) >> soapSpeciesList
		speciesResponse.species[0].guid == GUID
		speciesResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpeciesFull speciesFull = new SpeciesFull(guid: GUID)
		Species species = Mock()
		Page<Species> speciesPage = Mock()
		SpeciesFullRequest speciesFullRequest = new SpeciesFullRequest(guid: GUID)

		when:
		SpeciesFullResponse speciesFullResponse = speciesSoapReader.readFull(speciesFullRequest)

		then:
		1 * speciesSoapQueryBuilderMock.query(speciesFullRequest) >> speciesPage
		1 * speciesPage.content >> Lists.newArrayList(species)
		1 * speciesFullSoapMapperMock.mapFull(species) >> speciesFull
		speciesFullResponse.species.guid == GUID
	}

	void "requires GUID in full request"() {
		given:
		SpeciesFullRequest speciesFullRequest = Mock()

		when:
		speciesSoapReader.readFull(speciesFullRequest)

		then:
		thrown(MissingGUIDException)
	}

}
