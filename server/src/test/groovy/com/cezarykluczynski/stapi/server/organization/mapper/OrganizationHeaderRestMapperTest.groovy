package com.cezarykluczynski.stapi.server.organization.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationHeader
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class OrganizationHeaderRestMapperTest extends AbstractOrganizationMapperTest {

	private OrganizationHeaderRestMapper organizationHeaderRestMapper

	void setup() {
		organizationHeaderRestMapper = Mappers.getMapper(OrganizationHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Organization organization = new Organization(
				uid: UID,
				name: NAME)

		when:
		OrganizationHeader organizationHeader = organizationHeaderRestMapper.map(Lists.newArrayList(organization))[0]

		then:
		organizationHeader.uid == UID
		organizationHeader.name == NAME
	}

}
