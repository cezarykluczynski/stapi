package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import spock.lang.Specification

class StarshipClassTitleEnrichingProcessorTest extends Specification {

	private static final String TITLE = 'Ferengi shuttle'
	private static final String INVALID_TITLE = 'Federation shuttle'
	private static final String SPECIES_NAME = 'Ferengi'
	private static final String ORGANIZATION_NAME = 'Federation'

	private SpeciesRepository speciesRepositoryMock

	private StarshipClassTitleEnrichingProcessor starshipClassTitleEnrichingProcessor

	void setup() {
		speciesRepositoryMock = Mock()
		starshipClassTitleEnrichingProcessor = new StarshipClassTitleEnrichingProcessor(speciesRepositoryMock)
	}

	void "gets species from first word of class name"() {
		given:
		Species species = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		EnrichablePair<String, StarshipClassTemplate> enrichablePair = EnrichablePair.of(TITLE, starshipClassTemplate)

		when:
		starshipClassTitleEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * speciesRepositoryMock.findByName(SPECIES_NAME) >> Optional.of(species)
		0 * _
		starshipClassTemplate.species == species
	}

	void "does not set species when it is already set"() {
		given:
		Species species = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(species: species)
		EnrichablePair<String, StarshipClassTemplate> enrichablePair = EnrichablePair.of(INVALID_TITLE, starshipClassTemplate)

		when:
		starshipClassTitleEnrichingProcessor.enrich(enrichablePair)

		then:
		0 * _
		starshipClassTemplate.species == species
	}

	void "does not get species from first word of class name, if it is invalid"() {
		given:
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		EnrichablePair<String, StarshipClassTemplate> enrichablePair = EnrichablePair.of(INVALID_TITLE, starshipClassTemplate)

		when:
		starshipClassTitleEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * speciesRepositoryMock.findByName(ORGANIZATION_NAME) >> Optional.empty()
		0 * _
		starshipClassTemplate.species == null
	}

}
