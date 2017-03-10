package com.cezarykluczynski.stapi.server.species.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.species.entity.Species as DBSpecies
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesRestMapper
import com.cezarykluczynski.stapi.server.species.query.SpeciesRestQuery
import com.cezarykluczynski.stapi.client.v1.rest.model.Species as RESTSpecies
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpeciesRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private SpeciesRestQuery speciesRestQueryBuilderMock

	private SpeciesRestMapper speciesRestMapperMock

	private PageMapper pageMapperMock

	private SpeciesRestReader speciesRestReader

	void setup() {
		speciesRestQueryBuilderMock = Mock(SpeciesRestQuery)
		speciesRestMapperMock = Mock(SpeciesRestMapper)
		pageMapperMock = Mock(PageMapper)
		speciesRestReader = new SpeciesRestReader(speciesRestQueryBuilderMock, speciesRestMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into SpeciesResponse"() {
		given:
		List<DBSpecies> dbSpeciesList = Lists.newArrayList()
		Page<DBSpecies> dbSpeciesPage = Mock(Page)
		dbSpeciesPage.content >> dbSpeciesList
		List<RESTSpecies> soapSpeciesList = Lists.newArrayList(new RESTSpecies(guid: GUID))
		SpeciesRestBeanParams seriesRestBeanParams = Mock(SpeciesRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		SpeciesResponse speciesResponse = speciesRestReader.read(seriesRestBeanParams)

		then:
		1 * speciesRestQueryBuilderMock.query(seriesRestBeanParams) >> dbSpeciesPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbSpeciesPage) >> responsePage
		1 * speciesRestMapperMock.map(dbSpeciesList) >> soapSpeciesList
		speciesResponse.species[0].guid == GUID
		speciesResponse.page == responsePage
	}

}
