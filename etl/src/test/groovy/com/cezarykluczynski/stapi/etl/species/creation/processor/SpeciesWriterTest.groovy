package com.cezarykluczynski.stapi.etl.species.creation.processor

import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class SpeciesWriterTest extends Specification {

	private SpeciesRepository speciesRepositoryMock

	private SpeciesWriter speciesWriterMock

	void setup() {
		speciesRepositoryMock = Mock()
		speciesWriterMock = new SpeciesWriter(speciesRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Species species = new Species()
		List<Species> speciesList = Lists.newArrayList(species)

		when:
		speciesWriterMock.write(new Chunk(speciesList))

		then:
		1 * speciesRepositoryMock.saveAll(speciesList)
		0 * _
	}

}
