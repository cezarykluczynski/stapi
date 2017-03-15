package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams
import com.cezarykluczynski.stapi.server.company.reader.CompanyRestReader

class CompanyRestEndpointTest extends AbstractRestEndpointTest {

	private static final String GUID = 'GUID'
	private static final String NAME = 'TITLE'

	private CompanyRestReader companyRestReaderMock

	private CompanyRestEndpoint companyRestEndpoint

	void setup() {
		companyRestReaderMock = Mock(CompanyRestReader)
		companyRestEndpoint = new CompanyRestEndpoint(companyRestReaderMock)
	}

	void "passes get call to CompanyRestReader"() {
		given:
		CompanyFullResponse companyFullResponse = Mock(CompanyFullResponse)

		when:
		CompanyFullResponse companyFullResponseOutput = companyRestEndpoint.getCompany(GUID)

		then:
		1 * companyRestReaderMock.readFull(GUID) >> companyFullResponse
		companyFullResponseOutput == companyFullResponse
	}

	void "passes search get call to CompanyRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		CompanyBaseResponse companyResponse = Mock(CompanyBaseResponse)

		when:
		CompanyBaseResponse companyResponseOutput = companyRestEndpoint.searchCompanies(pageAwareBeanParams)

		then:
		1 * companyRestReaderMock.readBase(_ as CompanyRestBeanParams) >> { CompanyRestBeanParams companyRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			companyResponse
		}
		companyResponseOutput == companyResponse
	}

	void "passes search post call to CompanyRestReader"() {
		given:
		CompanyRestBeanParams companyRestBeanParams = new CompanyRestBeanParams(name: NAME)
		CompanyBaseResponse companyResponse = Mock(CompanyBaseResponse)

		when:
		CompanyBaseResponse companyResponseOutput = companyRestEndpoint.searchCompanies(companyRestBeanParams)

		then:
		1 * companyRestReaderMock.readBase(companyRestBeanParams as CompanyRestBeanParams) >> { CompanyRestBeanParams params ->
			assert params.name == NAME
			companyResponse
		}
		companyResponseOutput == companyResponse
	}

}
