package com.cezarykluczynski.stapi.etl.organization.creation.processor

import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class OrganizationWriterTest extends Specification {

	private OrganizationRepository organizationRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private OrganizationWriter organizationWriterMock

	void setup() {
		organizationRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		organizationWriterMock = new OrganizationWriter(organizationRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Organization organization = new Organization()
		List<Organization> organizationList = Lists.newArrayList(organization)

		when:
		organizationWriterMock.write(organizationList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Organization) >> { args ->
			assert args[0][0] == organization
			organizationList
		}
		1 * organizationRepositoryMock.save(organizationList)
		0 * _
	}

}
