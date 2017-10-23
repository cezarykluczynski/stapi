package com.cezarykluczynski.stapi.etl.organization.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class OrganizationProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private OrganizationPageProcessor organizationPageProcessorMock

	private OrganizationProcessor organizationProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		organizationPageProcessorMock = Mock()
		organizationProcessor = new OrganizationProcessor(pageHeaderProcessorMock, organizationPageProcessorMock)
	}

	void "converts PageHeader to Organization"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Organization organization = new Organization()

		when:
		Organization organizationOutput = organizationProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * organizationPageProcessorMock.process(page) >> organization

		then: 'last processor output is returned'
		organizationOutput == organization
	}

}
