package com.cezarykluczynski.stapi.etl.organization.creation.processor

import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class OrganizationWriterTest extends Specification {

	private OrganizationRepository organizationRepositoryMock

	private OrganizationWriter organizationWriterMock

	void setup() {
		organizationRepositoryMock = Mock()
		organizationWriterMock = new OrganizationWriter(organizationRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Organization organization = new Organization()
		List<Organization> organizationList = Lists.newArrayList(organization)

		when:
		organizationWriterMock.write(new Chunk(organizationList))

		then:
		1 * organizationRepositoryMock.saveAll(organizationList)
		0 * _
	}

}
