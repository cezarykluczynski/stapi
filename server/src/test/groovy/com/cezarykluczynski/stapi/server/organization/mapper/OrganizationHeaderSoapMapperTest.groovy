package com.cezarykluczynski.stapi.server.organization.mapper

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationHeader
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class OrganizationHeaderSoapMapperTest extends AbstractOrganizationMapperTest {

	private OrganizationHeaderSoapMapper organizationHeaderSoapMapper

	void setup() {
		organizationHeaderSoapMapper = Mappers.getMapper(OrganizationHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Organization organization = new Organization(
				uid: UID,
				name: NAME)

		when:
		OrganizationHeader organizationHeader = organizationHeaderSoapMapper.map(Lists.newArrayList(organization))[0]

		then:
		organizationHeader.uid == UID
		organizationHeader.name == NAME
	}

}
