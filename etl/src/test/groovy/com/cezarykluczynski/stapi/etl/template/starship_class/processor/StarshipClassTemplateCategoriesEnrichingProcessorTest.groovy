package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.etl.template.starship_class.service.OrganizationsStarshipClassesToOrganizationsMappingProvider
import com.cezarykluczynski.stapi.etl.template.starship_class.service.SpeciesStarshipClassesToSpeciesMappingProvider
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class StarshipClassTemplateCategoriesEnrichingProcessorTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'
	private static final String TITLE_MIRROR = 'TITLE_(mirror)'
	private static final String TITLE_ALTERNATE_REALITY = 'TITLE_(alternate_reality)'

	private SpeciesStarshipClassesToSpeciesMappingProvider speciesStarshipClassesToSpeciesMappingProviderMock

	private OrganizationsStarshipClassesToOrganizationsMappingProvider speciesStarshipClassesToOrganizationsMappingProviderMock

	private StarshipClassTemplateCategoriesEnrichingProcessor starshipClassTemplateCategoriesEnrichingProcessor

	void setup() {
		speciesStarshipClassesToSpeciesMappingProviderMock = Mock()
		speciesStarshipClassesToOrganizationsMappingProviderMock = Mock()
		starshipClassTemplateCategoriesEnrichingProcessor = new StarshipClassTemplateCategoriesEnrichingProcessor(
				speciesStarshipClassesToSpeciesMappingProviderMock, speciesStarshipClassesToOrganizationsMappingProviderMock)
	}

	void "when no species nor organization were found, nothing happens"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: TITLE_1))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, starshipClassTemplate))

		then:
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_1) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_1) >> Optional.empty()
		0 * _
		starshipClassTemplate.species == null
		starshipClassTemplate.affiliations.empty
		!starshipClassTemplate.alternateReality
	}

	void "when exactly one species was found, it is used"() {
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
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_1) >> Optional.empty()
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		0 * _
		starshipClassTemplate.species == species
		starshipClassTemplate.affiliations.empty
		!starshipClassTemplate.alternateReality
	}

	void "when exactly one organization was found, it is used"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: TITLE_1),
				new CategoryHeader(title: TITLE_2))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		Organization organization = Mock()

		when:
		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, starshipClassTemplate))

		then:
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_1) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_1) >> Optional.of(organization)
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		0 * _
		starshipClassTemplate.affiliations == Sets.newHashSet(organization)
		starshipClassTemplate.species == null
		!starshipClassTemplate.alternateReality
	}

	void "when more than one species was found, first one is used"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: TITLE_1),
				new CategoryHeader(title: TITLE_2),
				new CategoryHeader(title: TITLE_3))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		Species species1 = new Species(name: 'species1')
		Species species2 = new Species(name: 'species2')

		when:
		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, starshipClassTemplate))

		then:
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_1) >> Optional.of(species1)
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_1) >> Optional.empty()
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_3) >> Optional.of(species2)
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_3) >> Optional.empty()
		0 * _
		starshipClassTemplate.species == species1
		starshipClassTemplate.affiliations.empty
		!starshipClassTemplate.alternateReality
	}

	void "when more than one organization was found, both are used"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: TITLE_1),
				new CategoryHeader(title: TITLE_2),
				new CategoryHeader(title: TITLE_3))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		Organization organization1 = Mock()
		Organization organization2 = Mock()

		when:
		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, starshipClassTemplate))

		then:
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_1) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_1) >> Optional.of(organization1)
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_2) >> Optional.empty()
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_3) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_3) >> Optional.of(organization2)
		0 * _
		starshipClassTemplate.species == null
		starshipClassTemplate.affiliations == Sets.newHashSet(organization1, organization2)
		!starshipClassTemplate.alternateReality
	}

	void "sets alternateReality flag to true when title ends with string '(mirror)'"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: TITLE_MIRROR))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, starshipClassTemplate))

		then:
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_MIRROR) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_MIRROR) >> Optional.empty()
		0 * _
		starshipClassTemplate.species == null
		starshipClassTemplate.affiliations.empty
		starshipClassTemplate.mirror
		!starshipClassTemplate.alternateReality
	}

	void "sets alternateReality flag to true when title ends with string '(alternate_reality)'"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: TITLE_ALTERNATE_REALITY))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, starshipClassTemplate))

		then:
		1 * speciesStarshipClassesToSpeciesMappingProviderMock.provide(TITLE_ALTERNATE_REALITY) >> Optional.empty()
		1 * speciesStarshipClassesToOrganizationsMappingProviderMock.provide(TITLE_ALTERNATE_REALITY) >> Optional.empty()
		0 * _
		starshipClassTemplate.species == null
		starshipClassTemplate.affiliations.empty
		!starshipClassTemplate.mirror
		starshipClassTemplate.alternateReality
	}

}
