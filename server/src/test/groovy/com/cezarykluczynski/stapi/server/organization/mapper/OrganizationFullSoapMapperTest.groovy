package com.cezarykluczynski.stapi.server.organization.mapper

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFull
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import org.mapstruct.factory.Mappers

class OrganizationFullSoapMapperTest extends AbstractOrganizationMapperTest {

	private OrganizationFullSoapMapper organizationFullSoapMapper

	void setup() {
		organizationFullSoapMapper = Mappers.getMapper(OrganizationFullSoapMapper)
	}

	void "maps SOAP OrganizationFullRequest to OrganizationBaseRequestDTO"() {
		given:
		OrganizationFullRequest organizationFullRequest = new OrganizationFullRequest(uid: UID)

		when:
		OrganizationRequestDTO organizationRequestDTO = organizationFullSoapMapper.mapFull organizationFullRequest

		then:
		organizationRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Organization organization = createOrganization()

		when:
		OrganizationFull organizationFull = organizationFullSoapMapper.mapFull(organization)

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
