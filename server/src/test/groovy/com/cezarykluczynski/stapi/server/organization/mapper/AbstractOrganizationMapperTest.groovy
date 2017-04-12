package com.cezarykluczynski.stapi.server.organization.mapper

import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.util.AbstractOrganizationTest

abstract class AbstractOrganizationMapperTest extends AbstractOrganizationTest {

	protected static Organization createOrganization() {
		new Organization(
				guid: GUID,
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
	}

}
