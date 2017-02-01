package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams
import com.cezarykluczynski.stapi.server.company.reader.CompanyRestReader

class CompanyRestEndpointTest extends AbstractRestEndpointTest {

	private CompanyRestReader companyRestReaderMock

	private CompanyRestEndpoint companyRestEndpoint

	void setup() {
		companyRestReaderMock = Mock(CompanyRestReader)
		companyRestEndpoint = new CompanyRestEndpoint(companyRestReaderMock)
	}

	void "passes get call to CompanyRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		CompanyResponse companyResponse = Mock(CompanyResponse)

		when:
		CompanyResponse companyResponseOutput = companyRestEndpoint.getCompanies(pageAwareBeanParams)

		then:
		1 * companyRestReaderMock.read(_ as CompanyRestBeanParams) >> { CompanyRestBeanParams companyRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			companyResponse
		}
		companyResponseOutput == companyResponse
	}

	void "passes post call to CompanyRestReader"() {
		given:
		CompanyRestBeanParams companyRestBeanParams = new CompanyRestBeanParams()
		CompanyResponse companyResponse = Mock(CompanyResponse)

		when:
		CompanyResponse companyResponseOutput = companyRestEndpoint.searchCompanies(companyRestBeanParams)

		then:
		1 * companyRestReaderMock.read(companyRestBeanParams as CompanyRestBeanParams) >> { CompanyRestBeanParams params ->
			companyResponse
		}
		companyResponseOutput == companyResponse
	}

}
