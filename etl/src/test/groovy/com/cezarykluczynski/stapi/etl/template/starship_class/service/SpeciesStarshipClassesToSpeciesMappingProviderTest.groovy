package com.cezarykluczynski.stapi.etl.template.starship_class.service

import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import spock.lang.Specification

class SpeciesStarshipClassesToSpeciesMappingProviderTest extends Specification {

	private static final String INVALID_KEY = 'INVALID_KEY'
	private static final String VALID_KEY = 'Ferengi_starship_classes'
	private static final String VALID_NAME = 'Ferengi'

	private SpeciesRepository speciesRepositoryMock

	private SpeciesStarshipClassesToSpeciesMappingProvider speciesStarshipClassesToSpeciesMappingProvider

	void setup() {
		speciesRepositoryMock = Mock()
		speciesStarshipClassesToSpeciesMappingProvider = new SpeciesStarshipClassesToSpeciesMappingProvider(speciesRepositoryMock)
	}

	void "when no mappings is found, empty optional is returned"() {
		when:
		Optional<Species> speciesOptional = speciesStarshipClassesToSpeciesMappingProvider.provide(INVALID_KEY)

		then:
		0 * _
		!speciesOptional.isPresent()
	}

	void "when mappings is found, result of repository query is returned"() {
		given:
		Species species = Mock()
		Optional<Species> speciesOptional = Optional.of(species)

		when:
		Optional<Species> speciesOptionalOutput = speciesStarshipClassesToSpeciesMappingProvider.provide(VALID_KEY)

		then:
		1 * speciesRepositoryMock.findByName(VALID_NAME) >> speciesOptional
		0 * _
		speciesOptionalOutput == speciesOptional
	}

}
