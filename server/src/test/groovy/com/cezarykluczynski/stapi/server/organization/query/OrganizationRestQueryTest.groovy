package com.cezarykluczynski.stapi.server.organization.query

import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.organization.dto.OrganizationRestBeanParams
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class OrganizationRestQueryTest extends Specification {

	private OrganizationBaseRestMapper organizationBaseRestMapperMock

	private PageMapper pageMapperMock

	private OrganizationRepository organizationRepositoryMock

	private OrganizationRestQuery organizationRestQuery

	void setup() {
		organizationBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		organizationRepositoryMock = Mock()
		organizationRestQuery = new OrganizationRestQuery(organizationBaseRestMapperMock, pageMapperMock, organizationRepositoryMock)
	}

	void "maps OrganizationRestBeanParams to OrganizationRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		OrganizationRestBeanParams organizationRestBeanParams = Mock()
		OrganizationRequestDTO organizationRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = organizationRestQuery.query(organizationRestBeanParams)

		then:
		1 * organizationBaseRestMapperMock.mapBase(organizationRestBeanParams) >> organizationRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(organizationRestBeanParams) >> pageRequest
		1 * organizationRepositoryMock.findMatching(organizationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
