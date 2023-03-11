package com.cezarykluczynski.stapi.server.species.reader

import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2Base
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2Full
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2FullResponse
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.species.dto.SpeciesV2RestBeanParams
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper
import com.cezarykluczynski.stapi.server.species.query.SpeciesRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpeciesV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private SpeciesRestQuery speciesRestQueryBuilderMock

	private SpeciesBaseRestMapper speciesBaseRestMapperMock

	private SpeciesFullRestMapper speciesFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SpeciesV2RestReader speciesV2RestReader

	void setup() {
		speciesRestQueryBuilderMock = Mock()
		speciesBaseRestMapperMock = Mock()
		speciesFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		speciesV2RestReader = new SpeciesV2RestReader(speciesRestQueryBuilderMock, speciesBaseRestMapperMock,
				speciesFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpeciesV2Base speciesV2Base = Mock()
		Species species = Mock()
		SpeciesV2RestBeanParams speciesRestBeanParams = Mock()
		List<SpeciesV2Base> restSpeciesList = Lists.newArrayList(speciesV2Base)
		List<Species> speciesList = Lists.newArrayList(species)
		Page<Species> speciesPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		SpeciesV2BaseResponse speciesV2ResponseOutput = speciesV2RestReader.readBase(speciesRestBeanParams)

		then:
		1 * speciesRestQueryBuilderMock.query(speciesRestBeanParams) >> speciesPage
		1 * pageMapperMock.fromPageToRestResponsePage(speciesPage) >> responsePage
		1 * speciesRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * speciesPage.content >> speciesList
		1 * speciesBaseRestMapperMock.mapV2Base(speciesList) >> restSpeciesList
		0 * _
		speciesV2ResponseOutput.species == restSpeciesList
		speciesV2ResponseOutput.page == responsePage
		speciesV2ResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SpeciesV2Full speciesV2Full = Mock()
		Species species = Mock()
		List<Species> speciesList = Lists.newArrayList(species)
		Page<Species> speciesPage = Mock()

		when:
		SpeciesV2FullResponse speciesResponseOutput = speciesV2RestReader.readFull(UID)

		then:
		1 * speciesRestQueryBuilderMock.query(_ as SpeciesV2RestBeanParams) >> {
			SpeciesV2RestBeanParams speciesRestBeanParams ->
				assert speciesRestBeanParams.uid == UID
				speciesPage
		}
		1 * speciesPage.content >> speciesList
		1 * speciesFullRestMapperMock.mapV2Full(species) >> speciesV2Full
		0 * _
		speciesResponseOutput.species == speciesV2Full
	}

	void "requires UID in full request"() {
		when:
		speciesV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
