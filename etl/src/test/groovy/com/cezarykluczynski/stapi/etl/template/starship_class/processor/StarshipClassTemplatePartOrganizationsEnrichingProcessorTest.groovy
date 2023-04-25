package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class StarshipClassTemplatePartOrganizationsEnrichingProcessorTest extends Specification {

	private static final String OWNER = 'OWNER'
	private static final String OPERATOR = 'OPERATOR'
	private static final String AFFILIATION = 'AFFILIATION'
	private static final String PAGE_TITLE = 'PAGE_TITLE'
	private static final String ORGANIZATION_1 = 'ORGANIZATION_1'
	private static final String ORGANIZATION_2 = 'ORGANIZATION_2'

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private StarshipClassTemplatePartOrganizationsEnrichingProcessor starshipClassTemplatePartOrganizationsEnrichingProcessor

	void setup() {
		wikitextToEntitiesProcessorMock = Mock()
		starshipClassTemplatePartOrganizationsEnrichingProcessor = new StarshipClassTemplatePartOrganizationsEnrichingProcessor(
				wikitextToEntitiesProcessorMock)
	}

	void "when owner part is found, and WikitextToEntitiesProcessor returns no items, nothing happens"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.OWNER,
				value: OWNER)
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(page: new Page(title: PAGE_TITLE))

		when:
		starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OWNER) >> Lists.newArrayList()
		1 * wikitextToEntitiesProcessorMock.findOrganizations(part) >> Lists.newArrayList()
		0 * _
		starshipClassTemplate.owners.empty
	}

	void "when owner part is found, and WikitextToEntitiesProcessor returns one item, it is used as owner"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.T1OWNER,
				value: OWNER)
		Organization organization = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(page: new Page(title: PAGE_TITLE))

		when:
		starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OWNER) >> Lists.newArrayList(organization)
		1 * wikitextToEntitiesProcessorMock.findOrganizations(part) >> Lists.newArrayList()
		0 * _
		starshipClassTemplate.owners[0] == organization
	}

	void "when owner part is found, and WikitextToEntitiesProcessor returns two items, both are used"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.T5OWNER,
				value: OWNER)
		Organization organization1 = new Organization(name: ORGANIZATION_1)
		Organization organization2 = new Organization(name: ORGANIZATION_2)
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(page: new Page(title: PAGE_TITLE))

		when:
		starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OWNER) >> Lists.newArrayList(organization1)
		1 * wikitextToEntitiesProcessorMock.findOrganizations(part) >> Lists.newArrayList(organization2)
		0 * _
		starshipClassTemplate.owners == Sets.newHashSet(organization1, organization2)
	}

	void "when operator part is found, and WikitextToEntitiesProcessor returns no items, nothing happens"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.OPERATOR,
				value: OPERATOR)
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(page: new Page(title: PAGE_TITLE))

		when:
		starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OPERATOR) >> Lists.newArrayList()
		1 * wikitextToEntitiesProcessorMock.findOrganizations(part) >> Lists.newArrayList()
		0 * _
		starshipClassTemplate.operators.empty
	}

	void "when operator part is found, and WikitextToEntitiesProcessor returns one item, it is used as owner"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.T1OPERATOR,
				value: OPERATOR)
		Organization organization = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(page: new Page(title: PAGE_TITLE))

		when:
		starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OPERATOR) >> Lists.newArrayList()
		1 * wikitextToEntitiesProcessorMock.findOrganizations(part) >> Lists.newArrayList(organization)
		0 * _
		starshipClassTemplate.operators == Sets.newHashSet(organization)
	}

	void "when operator part is found, and WikitextToEntitiesProcessor returns two items, both are used"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.T5OPERATOR,
				value: OPERATOR)
		Organization organization1 = new Organization(name: ORGANIZATION_1)
		Organization organization2 = new Organization(name: ORGANIZATION_2)
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(page: new Page(title: PAGE_TITLE))

		when:
		starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(OPERATOR) >> Lists.newArrayList(organization1)
		1 * wikitextToEntitiesProcessorMock.findOrganizations(part) >> Lists.newArrayList(organization2)
		0 * _
		starshipClassTemplate.operators == Sets.newHashSet(organization1, organization2)
	}

	void "when affiliation part is found, and WikitextToEntitiesProcessor returns no items, nothing happens"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.AFFILIATION,
				value: AFFILIATION)
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(page: new Page(title: PAGE_TITLE))

		when:
		starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(AFFILIATION) >> Lists.newArrayList()
		1 * wikitextToEntitiesProcessorMock.findOrganizations(part) >> Lists.newArrayList()
		0 * _
		starshipClassTemplate.operators.empty
	}

	void "when affiliation part is found, and WikitextToEntitiesProcessor returns one item, it is used as owner"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.T1AFFILIATION,
				value: AFFILIATION)
		Organization organization = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(page: new Page(title: PAGE_TITLE))

		when:
		starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(AFFILIATION) >> Lists.newArrayList(organization)
		1 * wikitextToEntitiesProcessorMock.findOrganizations(part) >> Lists.newArrayList()
		0 * _
		starshipClassTemplate.affiliations == Sets.newHashSet(organization)
	}

	void "when affiliation part is found, and WikitextToEntitiesProcessor returns two items, both are used"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.T5AFFILIATION,
				value: AFFILIATION)
		Organization organization1 = new Organization(name: ORGANIZATION_1)
		Organization organization2 = new Organization(name: ORGANIZATION_2)
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(page: new Page(title: PAGE_TITLE))

		when:
		starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(AFFILIATION) >> Lists.newArrayList(organization1, organization2)
		1 * wikitextToEntitiesProcessorMock.findOrganizations(part) >> Lists.newArrayList()
		0 * _
		starshipClassTemplate.affiliations == Sets.newHashSet(organization1, organization2)
	}

}
