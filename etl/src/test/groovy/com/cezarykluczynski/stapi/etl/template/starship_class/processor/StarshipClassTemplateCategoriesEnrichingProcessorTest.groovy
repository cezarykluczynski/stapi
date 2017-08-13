package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.etl.template.starship_class.service.SpeciesStarshipClassesToSpeciesMappingProvider
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class StarshipClassTemplateCategoriesEnrichingProcessorTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'

	private SpeciesStarshipClassesToSpeciesMappingProvider speciesStarshipClassesToSpeciesMappingProviderMock

	private StarshipClassTemplateCategoriesEnrichingProcessor starshipClassTemplateCategoriesEnrichingProcessor

	void setup() {
		speciesStarshipClassesToSpeciesMappingProviderMock = Mock()
		starshipClassTemplateCategoriesEnrichingProcessor = new StarshipClassTemplateCategoriesEnrichingProcessor(
				speciesStarshipClassesToSpeciesMappingProviderMock)
	}

	void "when no species was found by SpeciesStarshipClassesToSpeciesMappingProvider, nothing happens"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: TITLE_1))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, starshipClassTemplate))

		then:
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_1) >> Optional.empty()
		0 * _
		starshipClassTemplate.affiliatedSpecies == null
	}

	void "when exactly one species was found by SpeciesStarshipClassesToSpeciesMappingProvider, it is used"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: TITLE_1),
				new CategoryHeader(title: TITLE_2))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		Species species = Mock()

		when:
		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, starshipClassTemplate))

		then:
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_1) >> Optional.of(species)
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		0 * _
		starshipClassTemplate.affiliatedSpecies == species
	}

	void "when more than one species was found by SpeciesStarshipClassesToSpeciesMappingProvider, none is used"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: TITLE_1),
				new CategoryHeader(title: TITLE_2),
				new CategoryHeader(title: TITLE_3))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		Species species1 = Mock()
		Species species2 = Mock()

		when:
		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, starshipClassTemplate))

		then:
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_1) >> Optional.of(species1)
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_3) >> Optional.of(species2)
		0 * _
		starshipClassTemplate.affiliatedSpecies == null
	}

}
