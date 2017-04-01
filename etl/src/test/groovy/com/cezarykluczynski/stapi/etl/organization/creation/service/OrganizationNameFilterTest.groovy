package com.cezarykluczynski.stapi.etl.organization.creation.service

import spock.lang.Specification

class OrganizationNameFilterTest extends Specification {

	private OrganizationNameFilter organizationNameFilter

	void setup() {
		organizationNameFilter = new OrganizationNameFilter()
	}

	void "returns true for organization"() {
		expect:
		organizationNameFilter.isAnOrganization('United Federation of Planets')
	}

	void "returns false for entity that is not an organization"() {
		expect:
		organizationNameFilter.isAnOrganization('Bank') == Boolean.FALSE
	}

	void "returns false for entity that is not listed"() {
		expect:
		organizationNameFilter.isAnOrganization('Not a real title') == null
	}

}
