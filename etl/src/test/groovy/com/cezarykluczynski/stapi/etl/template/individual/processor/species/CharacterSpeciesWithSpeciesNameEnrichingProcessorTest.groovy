package com.cezarykluczynski.stapi.etl.template.individual.processor.species

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.character.repository.CharacterSpeciesRepository
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import org.apache.commons.lang3.math.Fraction
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class CharacterSpeciesWithSpeciesNameEnrichingProcessorTest extends Specification {

	private static final String SPECIES_NAME = 'WIKITEXT'
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private CharacterSpeciesRepository characterSpeciesRepositoryMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private CharacterSpeciesWithSpeciesNameEnrichingProcessor characterSpeciesWithSpeciesNameEnrichingProcessor

	void setup() {
		characterSpeciesRepositoryMock = Mock()
		entityLookupByNameServiceMock = Mock()
		characterSpeciesWithSpeciesNameEnrichingProcessor = new CharacterSpeciesWithSpeciesNameEnrichingProcessor(
				characterSpeciesRepositoryMock, entityLookupByNameServiceMock)
	}

	void "if species was found by name, it is added to character species set"() {
		given:
		Fraction fraction = Fraction.getFraction(1, 2)
		Pair<String, Fraction> pair = Pair.of(SPECIES_NAME, fraction)
		Set<CharacterSpecies> characterSpeciesSet = Sets.newHashSet()
		Species species = new Species()
		CharacterSpecies characterSpecies = new CharacterSpecies()

		when:
		characterSpeciesWithSpeciesNameEnrichingProcessor.enrich(EnrichablePair.of(pair, characterSpeciesSet))

		then:
		1 * entityLookupByNameServiceMock.findSpeciesByName(SPECIES_NAME, SOURCE) >> Optional.of(species)
		1 * characterSpeciesRepositoryMock.findOrCreate(species, fraction) >> characterSpecies
		0 * _
		characterSpeciesSet.contains characterSpecies
	}

	void "if no species was found by name, character species set stays empty"() {
		given:
		Fraction fraction = Fraction.getFraction(1, 2)
		Pair<String, Fraction> pair = Pair.of(SPECIES_NAME, fraction)
		Set<CharacterSpecies> characterSpeciesSet = Sets.newHashSet()

		when:
		characterSpeciesWithSpeciesNameEnrichingProcessor.enrich(EnrichablePair.of(pair, characterSpeciesSet))

		then:
		1 * entityLookupByNameServiceMock.findSpeciesByName(SPECIES_NAME, SOURCE) >> Optional.empty()
		0 * _
		characterSpeciesSet.empty
	}

}
