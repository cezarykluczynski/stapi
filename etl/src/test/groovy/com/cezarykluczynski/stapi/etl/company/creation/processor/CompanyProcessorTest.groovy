package com.cezarykluczynski.stapi.etl.company.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class CompanyProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private CompanyPageProcessor companyPageProcessorMock

	private CompanyProcessor companyProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		companyPageProcessorMock = Mock()
		companyProcessor = new CompanyProcessor(pageHeaderProcessorMock, companyPageProcessorMock)
	}

	void "converts PageHeader to Company"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Company company = new Company()

		when:
		Company companyOutput = companyProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * companyPageProcessorMock.process(page) >> company

		then: 'last processor output is returned'
		companyOutput == company
	}

}
