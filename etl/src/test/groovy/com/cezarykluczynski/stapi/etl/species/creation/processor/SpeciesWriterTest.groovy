package com.cezarykluczynski.stapi.etl.species.creation.processor

import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class SpeciesWriterTest extends Specification {

	private SpeciesRepository speciesRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private SpeciesWriter speciesWriterMock

	void setup() {
		speciesRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		speciesWriterMock = new SpeciesWriter(speciesRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Species species = new Species()
		List<Species> speciesList = Lists.newArrayList(species)

		when:
		speciesWriterMock.write(speciesList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Species) >> { args ->
			assert args[0][0] == species
			speciesList
		}
		1 * speciesRepositoryMock.save(speciesList)
		0 * _
	}

}
