package com.cezarykluczynski.stapi.server.species.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBase
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFull
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper
import com.cezarykluczynski.stapi.server.species.query.SpeciesRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpeciesRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private SpeciesRestQuery speciesRestQueryBuilderMock

	private SpeciesBaseRestMapper speciesBaseRestMapperMock

	private SpeciesFullRestMapper speciesFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SpeciesRestReader speciesRestReader

	void setup() {
		speciesRestQueryBuilderMock = Mock()
		speciesBaseRestMapperMock = Mock()
		speciesFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		speciesRestReader = new SpeciesRestReader(speciesRestQueryBuilderMock, speciesBaseRestMapperMock, speciesFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpeciesBase speciesBase = Mock()
		Species species = Mock()
		SpeciesRestBeanParams speciesRestBeanParams = Mock()
		List<SpeciesBase> restSpeciesList = Lists.newArrayList(speciesBase)
		List<Species> speciesList = Lists.newArrayList(species)
		Page<Species> speciesPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		SpeciesBaseResponse speciesResponseOutput = speciesRestReader.readBase(speciesRestBeanParams)

		then:
		1 * speciesRestQueryBuilderMock.query(speciesRestBeanParams) >> speciesPage
		1 * pageMapperMock.fromPageToRestResponsePage(speciesPage) >> responsePage
		1 * speciesRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * speciesPage.content >> speciesList
		1 * speciesBaseRestMapperMock.mapBase(speciesList) >> restSpeciesList
		0 * _
		speciesResponseOutput.species == restSpeciesList
		speciesResponseOutput.page == responsePage
		speciesResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SpeciesFull speciesFull = Mock()
		Species species = Mock()
		List<Species> speciesList = Lists.newArrayList(species)
		Page<Species> speciesPage = Mock()

		when:
		SpeciesFullResponse speciesResponseOutput = speciesRestReader.readFull(UID)

		then:
		1 * speciesRestQueryBuilderMock.query(_ as SpeciesRestBeanParams) >> { SpeciesRestBeanParams speciesRestBeanParams ->
			assert speciesRestBeanParams.uid == UID
			speciesPage
		}
		1 * speciesPage.content >> speciesList
		1 * speciesFullRestMapperMock.mapFull(species) >> speciesFull
		0 * _
		speciesResponseOutput.species == speciesFull
	}

	void "requires UID in full request"() {
		when:
		speciesRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
