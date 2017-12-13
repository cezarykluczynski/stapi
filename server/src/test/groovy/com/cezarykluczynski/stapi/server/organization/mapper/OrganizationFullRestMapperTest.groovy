package com.cezarykluczynski.stapi.server.organization.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFull
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import org.mapstruct.factory.Mappers

class OrganizationFullRestMapperTest extends AbstractOrganizationMapperTest {

	private OrganizationFullRestMapper organizationFullRestMapper

	void setup() {
		organizationFullRestMapper = Mappers.getMapper(OrganizationFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Organization organization = createOrganization()

		when:
		OrganizationFull organizationFull = organizationFullRestMapper.mapFull(organization)

		then:
		organizationFull.uid == UID
		organizationFull.name == NAME
		organizationFull.government == GOVERNMENT
		organizationFull.intergovernmentalOrganization == INTERGOVERNMENTAL_ORGANIZATION
		organizationFull.researchOrganization == RESEARCH_ORGANIZATION
		organizationFull.sportOrganization == SPORT_ORGANIZATION
		organizationFull.medicalOrganization == MEDICAL_ORGANIZATION
		organizationFull.militaryOrganization == MILITARY_ORGANIZATION
		organizationFull.militaryUnit == MILITARY_UNIT
		organizationFull.governmentAgency == GOVERNMENT_AGENCY
		organizationFull.lawEnforcementAgency == LAW_ENFORCEMENT_AGENCY
		organizationFull.prisonOrPenalColony == PRISON_OR_PENAL_COLONY
		organizationFull.mirror == MIRROR
		organizationFull.alternateReality == ALTERNATE_REALITY
		organizationFull.characters.size() == organization.characters.size()
	}

}
