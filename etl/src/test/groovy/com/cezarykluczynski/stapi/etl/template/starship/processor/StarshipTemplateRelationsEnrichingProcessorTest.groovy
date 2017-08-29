package com.cezarykluczynski.stapi.etl.template.starship.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.organization.WikitextToOrganizationsProcessor
import com.cezarykluczynski.stapi.etl.common.processor.spacecraft_class.WikitextToSpacecraftClassesProcessor
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplateParameter
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class StarshipTemplateRelationsEnrichingProcessorTest extends Specification {

	private static final String OWNER = 'OWNER'
	private static final String OPERATOR = 'OPERATOR'
	private static final String CLASS = 'CLASS'

	private WikitextToOrganizationsProcessor wikitextToOrganizationsProcessorMock

	private WikitextToSpacecraftClassesProcessor wikitextToSpacecraftClassesProcessorMock

	private StarshipTemplateRelationsEnrichingProcessor starshipTemplateRelationsEnrichingProcessor

	void setup() {
		wikitextToOrganizationsProcessorMock = Mock()
		wikitextToSpacecraftClassesProcessorMock = Mock()
		starshipTemplateRelationsEnrichingProcessor = new StarshipTemplateRelationsEnrichingProcessor(wikitextToOrganizationsProcessorMock,
				wikitextToSpacecraftClassesProcessorMock)
	}

	void "when owner part is found, and WikitextToOrganizationsProcessor returns no items, nothing happens"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.OWNER,
				value: OWNER)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OWNER) >> Lists.newArrayList()
		0 * _
		starshipTemplate.owner == null
	}

	void "when owner part is found, and WikitextToOrganizationsProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.OWNER,
				value: OWNER)))
		Organization organization = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OWNER) >> Lists.newArrayList(organization)
		0 * _
		starshipTemplate.owner == organization
	}

	void "when owner part is found, and WikitextToOrganizationsProcessor returns two items, first one is used"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.OWNER,
				value: OWNER)))
		Organization organization1 = Mock()
		Organization organization2 = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OWNER) >> Lists.newArrayList(organization1, organization2)
		0 * _
		starshipTemplate.owner == organization1
	}

	void "when operator part is found, and WikitextToOrganizationsProcessor returns no items, nothing happens"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.OPERATOR,
				value: OPERATOR)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OPERATOR) >> Lists.newArrayList()
		0 * _
		starshipTemplate.operator == null
	}

	void "when operator part is found, and WikitextToOrganizationsProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.OPERATOR,
				value: OPERATOR)))
		Organization organization = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OPERATOR) >> Lists.newArrayList(organization)
		0 * _
		starshipTemplate.operator == organization
	}

	void "when operator part is found, and WikitextToOrganizationsProcessor returns two items, first one is used"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.OPERATOR,
				value: OPERATOR)))
		Organization organization1 = Mock()
		Organization organization2 = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OPERATOR) >> Lists.newArrayList(organization1, organization2)
		0 * _
		starshipTemplate.operator == organization1
	}

	void "when class part is found, and WikitextToSpacecraftClassesProcessor returns no items, nothing happens"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.CLASS,
				value: CLASS)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToSpacecraftClassesProcessorMock.process(CLASS) >> Lists.newArrayList()
		0 * _
		starshipTemplate.spacecraftClass == null
	}

	void "when class part is found, and WikitextToSpacecraftClassesProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.CLASS,
				value: CLASS)))
		SpacecraftClass spacecraftClass = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToSpacecraftClassesProcessorMock.process(CLASS) >> Lists.newArrayList(spacecraftClass)
		0 * _
		starshipTemplate.spacecraftClass == spacecraftClass
	}

	void "when class part is found, and WikitextToSpacecraftClassesProcessor returns two items, first one is used"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.CLASS,
				value: CLASS)))
		SpacecraftClass spacecraftClass1 = Mock()
		SpacecraftClass spacecraftClass2 = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToSpacecraftClassesProcessorMock.process(CLASS) >> Lists.newArrayList(spacecraftClass1, spacecraftClass2)
		0 * _
		starshipTemplate.spacecraftClass == spacecraftClass1
	}

}
