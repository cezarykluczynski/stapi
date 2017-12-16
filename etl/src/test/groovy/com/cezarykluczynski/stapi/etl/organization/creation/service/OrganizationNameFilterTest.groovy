package com.cezarykluczynski.stapi.etl.organization.creation.service

import spock.lang.Specification

class OrganizationNameFilterTest extends Specification {

	private OrganizationNameFilter organizationNameFilter

	void setup() {
		organizationNameFilter = new OrganizationNameFilter()
	}

	void "returns match for organization"() {
		expect:
		organizationNameFilter.isAnOrganization('United Federation of Planets') == OrganizationNameFilter.Match.IS_AN_ORGANIZATION
	}

	void "returns false match for entity that is not an organization"() {
		expect:
		organizationNameFilter.isAnOrganization('Garrison')  == OrganizationNameFilter.Match.IS_NOT_AN_ORGANIZATION
	}

	void "returns empty match for entity that is not listed"() {
		expect:
		organizationNameFilter.isAnOrganization('Not a real title')  == OrganizationNameFilter.Match.UNKNOWN_RESULT
	}

}
