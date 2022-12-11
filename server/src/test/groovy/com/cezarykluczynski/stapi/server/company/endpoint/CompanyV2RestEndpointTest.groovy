package com.cezarykluczynski.stapi.server.company.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.company.dto.CompanyV2RestBeanParams
import com.cezarykluczynski.stapi.server.company.reader.CompanyV2RestReader

class CompanyV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private CompanyV2RestReader companyV2RestReaderMock

	private CompanyV2RestEndpoint companyV2RestEndpoint

	void setup() {
		companyV2RestReaderMock = Mock()
		companyV2RestEndpoint = new CompanyV2RestEndpoint(companyV2RestReaderMock)
	}

	void "passes get call to CompanyRestReader"() {
		given:
		CompanyV2FullResponse companyV2FullResponse = Mock()

		when:
		CompanyV2FullResponse companyV2FullResponseOutput = companyV2RestEndpoint.getCompany(UID)

		then:
		1 * companyV2RestReaderMock.readFull(UID) >> companyV2FullResponse
		companyV2FullResponseOutput == companyV2FullResponse
	}

	void "passes search get call to CompanyRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		CompanyV2BaseResponse companyV2Response = Mock()

		when:
		CompanyV2BaseResponse companyV2ResponseOutput = companyV2RestEndpoint
				.searchCompany(pageAwareBeanParams)

		then:
		1 * companyV2RestReaderMock.readBase(_ as CompanyV2RestBeanParams) >> { CompanyV2RestBeanParams companyRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			companyV2Response
		}
		companyV2ResponseOutput == companyV2Response
	}

	void "passes search post call to CompanyRestReader"() {
		given:
		CompanyV2RestBeanParams companyV2RestBeanParams = new CompanyV2RestBeanParams(name: NAME)
		CompanyV2BaseResponse companyV2Response = Mock()

		when:
		CompanyV2BaseResponse companyV2ResponseOutput = companyV2RestEndpoint.searchCompany(companyV2RestBeanParams)

		then:
		1 * companyV2RestReaderMock.readBase(companyV2RestBeanParams as CompanyV2RestBeanParams) >> {
			CompanyV2RestBeanParams params ->
				assert params.name == NAME
				companyV2Response
		}
		companyV2ResponseOutput == companyV2Response
	}

}
