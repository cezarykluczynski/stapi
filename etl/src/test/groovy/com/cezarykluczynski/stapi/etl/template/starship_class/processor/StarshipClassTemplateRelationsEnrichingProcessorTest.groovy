package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.organization.WikitextToOrganizationsProcessor
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

	private WikitextToOrganizationsProcessor wikitextToOrganizationsProcessorMock

	private StarshipClassTemplateRelationsEnrichingProcessor starshipClassTemplateRelationsEnrichingProcessor

	void setup() {
		starshipClassSpacecraftTypeProcessorMock = Mock()
		wikitextToOrganizationsProcessorMock = Mock()
		starshipClassTemplateRelationsEnrichingProcessor = new StarshipClassTemplateRelationsEnrichingProcessor(
				starshipClassSpacecraftTypeProcessorMock, wikitextToOrganizationsProcessorMock)
	}

	void "when owner part is found, and WikitextToOrganizationsProcessor returns no items, nothing happens"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OWNER,
				value: OWNER)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OWNER) >> Sets.newHashSet()
		0 * _
		starshipClassTemplate.owner == null
	}

	void "when owner part is found, and WikitextToOrganizationsProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OWNER,
				value: OWNER)))
		Organization organization = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OWNER) >> Sets.newHashSet(organization)
		0 * _
		starshipClassTemplate.owner == organization
	}

	void "when owner part is found, and WikitextToOrganizationsProcessor returns two items, nothing happens"() {
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
		1 * wikitextToOrganizationsProcessorMock.process(OWNER) >> Sets.newHashSet(organization1, organization2)
		0 * _
		starshipClassTemplate.operator == null
	}

	void "when operator part is found, and WikitextToOrganizationsProcessor returns no items, nothing happens"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OPERATOR,
				value: OPERATOR)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OPERATOR) >> Sets.newHashSet()
		0 * _
		starshipClassTemplate.operator == null
	}

	void "when operator part is found, and WikitextToOrganizationsProcessor returns one item, it is used as owner"() {
		given:
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.OPERATOR,
				value: OPERATOR)))
		Organization organization = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * wikitextToOrganizationsProcessorMock.process(OPERATOR) >> Sets.newHashSet(organization)
		0 * _
		starshipClassTemplate.operator == organization
	}

	void "when operator part is found, and WikitextToOrganizationsProcessor returns two items, nothing happens"() {
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
		1 * wikitextToOrganizationsProcessorMock.process(OPERATOR) >> Sets.newHashSet(organization1, organization2)
		0 * _
		starshipClassTemplate.owner == null
	}

	void "when active part is found, StarshipClassActivityPeriodProcessor is used to process it"() {
		given:
		Template sidebarStarshipClassTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.TYPE,
				value: TYPE)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		SpacecraftType spacecraftType = Mock()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipClassTemplate, starshipClassTemplate))

		then:
		1 * starshipClassSpacecraftTypeProcessorMock.process(TYPE) >> spacecraftType
		0 * _
		starshipClassTemplate.spacecraftType == spacecraftType
	}

}
