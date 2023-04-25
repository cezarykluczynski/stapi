package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Maps
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification

class LinkingTemplatesToEntitiesProcessorTest extends Specification {

	RepositoryProvider repositoryProviderMock

	LinkingTemplatesToEntitiesProcessor linkingTemplatesToEntitiesProcessor

	void setup() {
		repositoryProviderMock = Mock()
		linkingTemplatesToEntitiesProcessor = new LinkingTemplatesToEntitiesProcessor(repositoryProviderMock)
	}

	void "finds organization by template part"() {
		given:
		OrganizationRepository organizationRepositoryMock = Mock()
		Map<Class, CrudRepository> classCrudRepositoryMap = Maps.newHashMap()
		classCrudRepositoryMap.put(Organization, organizationRepositoryMock)
		Template.Part part = new Template.Part(templates: [
				new Template(title: 'federation')
		])
		Organization organization = Mock()

		when:
		List<Organization> organizations = linkingTemplatesToEntitiesProcessor.process(part, Organization)

		then:
		1 * repositoryProviderMock.provide() >> classCrudRepositoryMap
		1 * organizationRepositoryMock.findByName('United Federation of Planets') >> Optional.of(organization)
		0 * _
		organizations == [organization]
	}

	void "skips unknsown template list"() {
		given:
		OrganizationRepository organizationRepositoryMock = Mock()
		Map<Class, CrudRepository> classCrudRepositoryMap = Maps.newHashMap()
		classCrudRepositoryMap.put(Organization, organizationRepositoryMock)
		Template.Part part = new Template.Part(templates: [
				new Template(title: TemplateTitle.BGINFO),
				new Template(title: TemplateTitle.ASIN)
		])

		when:
		List<Organization> organizations = linkingTemplatesToEntitiesProcessor.process(part, Organization)

		then:
		1 * repositoryProviderMock.provide() >> classCrudRepositoryMap
		0 * _
		organizations == []
	}

}
