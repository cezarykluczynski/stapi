package com.cezarykluczynski.stapi.server.organization.mapper

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBase
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class OrganizationBaseSoapMapperTest extends AbstractOrganizationMapperTest {

	private OrganizationBaseSoapMapper organizationBaseSoapMapper

	void setup() {
		organizationBaseSoapMapper = Mappers.getMapper(OrganizationBaseSoapMapper)
	}

	void "maps SOAP OrganizationBaseRequest to OrganizationRequestDTO"() {
		given:
		OrganizationBaseRequest organizationBaseRequest = new OrganizationBaseRequest(
				name: NAME,
				government: GOVERNMENT,
				intergovernmentalOrganization: INTERGOVERNMENTAL_ORGANIZATION,
				researchOrganization: RESEARCH_ORGANIZATION,
				sportOrganization: SPORT_ORGANIZATION,
				medicalOrganization: MEDICAL_ORGANIZATION,
				militaryOrganization: MILITARY_ORGANIZATION,
				militaryUnit: MILITARY_UNIT,
				governmentAgency: GOVERNMENT_AGENCY,
				lawEnforcementAgency: LAW_ENFORCEMENT_AGENCY,
				prisonOrPenalColony: PRISON_OR_PENAL_COLONY,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)

		when:
		OrganizationRequestDTO organizationRequestDTO = organizationBaseSoapMapper.mapBase organizationBaseRequest

		then:
		organizationRequestDTO.name == NAME
		organizationRequestDTO.government == GOVERNMENT
		organizationRequestDTO.intergovernmentalOrganization == INTERGOVERNMENTAL_ORGANIZATION
		organizationRequestDTO.researchOrganization == RESEARCH_ORGANIZATION
		organizationRequestDTO.sportOrganization == SPORT_ORGANIZATION
		organizationRequestDTO.medicalOrganization == MEDICAL_ORGANIZATION
		organizationRequestDTO.militaryOrganization == MILITARY_ORGANIZATION
		organizationRequestDTO.militaryUnit == MILITARY_UNIT
		organizationRequestDTO.governmentAgency == GOVERNMENT_AGENCY
		organizationRequestDTO.lawEnforcementAgency == LAW_ENFORCEMENT_AGENCY
		organizationRequestDTO.prisonOrPenalColony == PRISON_OR_PENAL_COLONY
		organizationRequestDTO.mirror == MIRROR
		organizationRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Organization organization = createOrganization()

		when:
		OrganizationBase organizationBase = organizationBaseSoapMapper.mapBase(Lists.newArrayList(organization))[0]

		then:
		organizationBase.uid == UID
		organizationBase.name == NAME
		organizationBase.government == GOVERNMENT
		organizationBase.intergovernmentalOrganization == INTERGOVERNMENTAL_ORGANIZATION
		organizationBase.researchOrganization == RESEARCH_ORGANIZATION
		organizationBase.sportOrganization == SPORT_ORGANIZATION
		organizationBase.medicalOrganization == MEDICAL_ORGANIZATION
		organizationBase.militaryOrganization == MILITARY_ORGANIZATION
		organizationBase.militaryUnit == MILITARY_UNIT
		organizationBase.governmentAgency == GOVERNMENT_AGENCY
		organizationBase.lawEnforcementAgency == LAW_ENFORCEMENT_AGENCY
		organizationBase.prisonOrPenalColony == PRISON_OR_PENAL_COLONY
		organizationBase.mirror == MIRROR
		organizationBase.alternateReality == ALTERNATE_REALITY
	}

}
