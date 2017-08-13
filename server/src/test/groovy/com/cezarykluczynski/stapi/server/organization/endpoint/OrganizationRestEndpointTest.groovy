package com.cezarykluczynski.stapi.server.organization.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.organization.dto.OrganizationRestBeanParams
import com.cezarykluczynski.stapi.server.organization.reader.OrganizationRestReader

class OrganizationRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private OrganizationRestReader organizationRestReaderMock

	private OrganizationRestEndpoint organizationRestEndpoint

	void setup() {
		organizationRestReaderMock = Mock()
		organizationRestEndpoint = new OrganizationRestEndpoint(organizationRestReaderMock)
	}

	void "passes get call to OrganizationRestReader"() {
		given:
		OrganizationFullResponse organizationFullResponse = Mock()

		when:
		OrganizationFullResponse organizationFullResponseOutput = organizationRestEndpoint.getOrganization(UID)

		then:
		1 * organizationRestReaderMock.readFull(UID) >> organizationFullResponse
		organizationFullResponseOutput == organizationFullResponse
	}

	void "passes search get call to OrganizationRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		OrganizationBaseResponse organizationResponse = Mock()

		when:
		OrganizationBaseResponse organizationResponseOutput = organizationRestEndpoint.searchOrganizations(pageAwareBeanParams)

		then:
		1 * organizationRestReaderMock.readBase(_ as OrganizationRestBeanParams) >> { OrganizationRestBeanParams organizationRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			organizationResponse
		}
		organizationResponseOutput == organizationResponse
	}

	void "passes search post call to OrganizationRestReader"() {
		given:
		OrganizationRestBeanParams organizationRestBeanParams = new OrganizationRestBeanParams(name: NAME)
		OrganizationBaseResponse organizationResponse = Mock()

		when:
		OrganizationBaseResponse organizationResponseOutput = organizationRestEndpoint.searchOrganizations(organizationRestBeanParams)

		then:
		1 * organizationRestReaderMock.readBase(organizationRestBeanParams as OrganizationRestBeanParams) >> { OrganizationRestBeanParams params ->
			assert params.name == NAME
			organizationResponse
		}
		organizationResponseOutput == organizationResponse
	}

}
