package com.cezarykluczynski.stapi.etl.template.spacecraft.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.spacecraft.dto.SpacecraftTemplateParameter
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplateParameter
import com.cezarykluczynski.stapi.etl.template.starship.processor.ClassTemplateSpacecraftClassesProcessor
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class SpacecraftTemplateRelationsEnrichingProcessorTest extends Specification {

	private static final String OWNER = 'OWNER'
	private static final String OPERATOR = 'OPERATOR'
	private static final String CLASS = 'CLASS'

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private ClassTemplateSpacecraftClassesProcessor classTemplateSpacecraftClassesProcessorMock

	private SpacecraftTemplateRelationsEnrichingProcessor spacecraftTemplateRelationsEnrichingProcessor

	void setup() {
		wikitextToEntitiesProcessorMock = Mock()
		classTemplateSpacecraftClassesProcessorMock = Mock()
		spacecraftTemplateRelationsEnrichingProcessor = new SpacecraftTemplateRelationsEnrichingProcessor(wikitextToEntitiesProcessorMock,
				classTemplateSpacecraftClassesProcessorMock)
	}

	void "when owner part is found, and WikitextToEntitiesProcessor returns no items, nothing happens"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: SpacecraftTemplateParameter.OWNER,
				value: OWNER)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OWNER) >> Lists.newArrayList()
		0 * _
		starshipTemplate.owner == null
	}

	void "when owner part is found, and WikitextToEntitiesProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: SpacecraftTemplateParameter.OWNER,
				value: OWNER)))
		Organization organization = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OWNER) >> Lists.newArrayList(organization)
		0 * _
		starshipTemplate.owner == organization
	}

	void "when owner part is found, and WikitextToEntitiesProcessor returns two items, first one is used"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: SpacecraftTemplateParameter.OWNER,
				value: OWNER)))
		Organization organization1 = Mock()
		Organization organization2 = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OWNER) >> Lists.newArrayList(organization1, organization2)
		0 * _
		starshipTemplate.owner == organization1
	}

	void "when operator part is found, and WikitextToEntitiesProcessor returns no items, nothing happens"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: SpacecraftTemplateParameter.OPERATOR,
				value: OPERATOR)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OPERATOR) >> Lists.newArrayList()
		0 * _
		starshipTemplate.operator == null
	}

	void "when operator part is found, and WikitextToEntitiesProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: SpacecraftTemplateParameter.OPERATOR,
				value: OPERATOR)))
		Organization organization = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OPERATOR) >> Lists.newArrayList(organization)
		0 * _
		starshipTemplate.operator == organization
	}

	void "when operator part is found, and WikitextToEntitiesProcessor returns two items, first one is used"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: SpacecraftTemplateParameter.OPERATOR,
				value: OPERATOR)))
		Organization organization1 = Mock()
		Organization organization2 = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OPERATOR) >> Lists.newArrayList(organization1, organization2)
		0 * _
		starshipTemplate.operator == organization1
	}

	void "when class part is found, and WikitextToEntitiesProcessor returns two items, first one is used"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.CLASS,
				value: CLASS)))
		SpacecraftClass spacecraftClass1 = Mock()
		SpacecraftClass spacecraftClass2 = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findSpacecraftClasses(CLASS) >> Lists.newArrayList(spacecraftClass1, spacecraftClass2)
		0 * _
		starshipTemplate.spacecraftClass == spacecraftClass1
	}

	void "when class part is found, and WikitextToEntitiesProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.CLASS,
				value: CLASS)))
		SpacecraftClass spacecraftClass = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findSpacecraftClasses(CLASS) >> Lists.newArrayList(spacecraftClass)
		0 * _
		starshipTemplate.spacecraftClass == spacecraftClass
	}

	@SuppressWarnings('BracesForMethod')
	void """when class part is found, and WikitextToEntitiesProcessor returns no items, ClassTemplateSpacecraftClassesProcessor is used
			to retrieve spacecraft classes"""() {
		given:
		Template.Part templatePart = new Template.Part(
				key: StarshipTemplateParameter.CLASS,
				value: CLASS)
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(templatePart))
		StarshipTemplate starshipTemplate = new StarshipTemplate()
		SpacecraftClass spacecraftClass = Mock()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findSpacecraftClasses(CLASS) >> Lists.newArrayList()
		1 * classTemplateSpacecraftClassesProcessorMock.process(templatePart) >> Lists.newArrayList(spacecraftClass)
		0 * _
		starshipTemplate.spacecraftClass == spacecraftClass
	}

	@SuppressWarnings('BracesForMethod')
	void """when class part is found, and WikitextToEntitiesProcessor returns no items, and ClassTemplateSpacecraftClassesProcessor
			returns two items, first one is used"""() {
		given:
		Template.Part templatePart = new Template.Part(
				key: StarshipTemplateParameter.CLASS,
				value: CLASS)
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(templatePart))
		StarshipTemplate starshipTemplate = new StarshipTemplate()
		SpacecraftClass spacecraftClass1 = Mock()
		SpacecraftClass spacecraftClass2 = Mock()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findSpacecraftClasses(CLASS) >> Lists.newArrayList()
		1 * classTemplateSpacecraftClassesProcessorMock.process(templatePart) >> Lists.newArrayList(spacecraftClass1, spacecraftClass2)
		0 * _
		starshipTemplate.spacecraftClass == spacecraftClass1
	}

	@SuppressWarnings('BracesForMethod')
	void """when class part is found, and WikitextToEntitiesProcessor returns no items, ClassTemplateSpacecraftClassesProcessor returns
			no items, spacecraftClass is set to null"""() {
		given:
		Template.Part templatePart = new Template.Part(
				key: StarshipTemplateParameter.CLASS,
				value: CLASS)
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(templatePart))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findSpacecraftClasses(CLASS) >> Lists.newArrayList()
		1 * classTemplateSpacecraftClassesProcessorMock.process(templatePart) >> Lists.newArrayList()
		0 * _
		starshipTemplate.spacecraftClass == null
	}

}
