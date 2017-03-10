package com.cezarykluczynski.stapi.server.species.reader

import com.cezarykluczynski.stapi.client.v1.soap.Species as SOAPSpecies
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.species.entity.Species as DBSpecies
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesSoapMapper
import com.cezarykluczynski.stapi.server.species.query.SpeciesSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpeciesSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private SpeciesSoapQuery speciesSoapQueryBuilderMock

	private SpeciesSoapMapper speciesSoapMapperMock

	private PageMapper pageMapperMock

	private SpeciesSoapReader speciesSoapReader

	void setup() {
		speciesSoapQueryBuilderMock = Mock(SpeciesSoapQuery)
		speciesSoapMapperMock = Mock(SpeciesSoapMapper)
		pageMapperMock = Mock(PageMapper)
		speciesSoapReader = new SpeciesSoapReader(speciesSoapQueryBuilderMock, speciesSoapMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into SpeciesResponse"() {
		given:
		List<DBSpecies> dbSpeciesList = Lists.newArrayList()
		Page<DBSpecies> dbSpeciesPage = Mock(Page)
		dbSpeciesPage.content >> dbSpeciesList
		List<SOAPSpecies> soapSpeciesList = Lists.newArrayList(new SOAPSpecies(guid: GUID))
		SpeciesRequest speciesRequest = Mock(SpeciesRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		SpeciesResponse speciesResponse = speciesSoapReader.read(speciesRequest)

		then:
		1 * speciesSoapQueryBuilderMock.query(speciesRequest) >> dbSpeciesPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbSpeciesPage) >> responsePage
		1 * speciesSoapMapperMock.map(dbSpeciesList) >> soapSpeciesList
		speciesResponse.species[0].guid == GUID
		speciesResponse.page == responsePage
	}

}
