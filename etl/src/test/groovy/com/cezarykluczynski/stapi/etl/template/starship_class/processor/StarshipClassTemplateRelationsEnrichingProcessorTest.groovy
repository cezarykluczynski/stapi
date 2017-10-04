package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class StarshipClassTemplateRelationsEnrichingProcessorTest extends Specification {

	private static final String OWNER = 'OWNER'
	private static final String OPERATOR = 'OPERATOR'
	private static final String TYPE = 'TYPE'

	private StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private StarshipClassTemplateRelationsEnrichingProcessor starshipClassTemplateRelationsEnrichingProcessor

	void setup() {
		starshipClassSpacecraftTypeProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		starshipClassTemplateRelationsEnrichingProcessor = new StarshipClassTemplateRelationsEnrichingProcessor(
				starshipClassSpacecraftTypeProcessorMock, wikitextToEntitiesProcessorMock)
	}

	void "when owner part is found, and WikitextToEntitiesProcessor returns no items, nothing happens"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OWNER,
				value: OWNER)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OWNER) >> Lists.newArrayList()
		0 * _
		starshipClassTemplate.owner == null
	}

	void "when owner part is found, and WikitextToEntitiesProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OWNER,
				value: OWNER)))
		Organization organization = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OWNER) >> Lists.newArrayList(organization)
		0 * _
		starshipClassTemplate.owner == organization
	}

	void "when owner part is found, and WikitextToEntitiesProcessor returns two items, first one is used"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OWNER,
				value: OWNER)))
		Organization organization1 = Mock()
		Organization organization2 = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OWNER) >> Lists.newArrayList(organization1, organization2)
		0 * _
		starshipClassTemplate.owner == organization1
	}

	void "when operator part is found, and WikitextToEntitiesProcessor returns no items, nothing happens"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OPERATOR,
				value: OPERATOR)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OPERATOR) >> Lists.newArrayList()
		0 * _
		starshipClassTemplate.operator == null
	}

	void "when operator part is found, and WikitextToEntitiesProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OPERATOR,
				value: OPERATOR)))
		Organization organization = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OPERATOR) >> Lists.newArrayList(organization)
		0 * _
		starshipClassTemplate.operator == organization
	}

	void "when operator part is found, and WikitextToEntitiesProcessor returns two items, first one is used"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OPERATOR,
				value: OPERATOR)))
		Organization organization1 = Mock()
		Organization organization2 = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OPERATOR) >> Lists.newArrayList(organization1, organization2)
		0 * _
		starshipClassTemplate.operator == organization1
	}

	void "when type part is found, StarshipClassActivityPeriodProcessor is used to process it"() {
		given:
		Template sidebarStarshipClassTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.TYPE,
				value: TYPE)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		SpacecraftType spacecraftType1 = Mock()
		SpacecraftType spacecraftType2 = Mock()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipClassTemplate, starshipClassTemplate))

		then:
		1 * starshipClassSpacecraftTypeProcessorMock.process(TYPE) >> Sets.newHashSet(spacecraftType1, spacecraftType2)
		0 * _
		starshipClassTemplate.spacecraftTypes.contains spacecraftType1
		starshipClassTemplate.spacecraftTypes.contains spacecraftType2
	}

}
