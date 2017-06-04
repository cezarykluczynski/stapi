package com.cezarykluczynski.stapi.server.organization.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBase
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFull
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.organization.dto.OrganizationRestBeanParams
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullRestMapper
import com.cezarykluczynski.stapi.server.organization.query.OrganizationRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class OrganizationRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private OrganizationRestQuery organizationRestQueryBuilderMock

	private OrganizationBaseRestMapper organizationBaseRestMapperMock

	private OrganizationFullRestMapper organizationFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private OrganizationRestReader organizationRestReader

	void setup() {
		organizationRestQueryBuilderMock = Mock()
		organizationBaseRestMapperMock = Mock()
		organizationFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		organizationRestReader = new OrganizationRestReader(organizationRestQueryBuilderMock, organizationBaseRestMapperMock,
				organizationFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		OrganizationBase organizationBase = Mock()
		Organization organization = Mock()
		OrganizationRestBeanParams organizationRestBeanParams = Mock()
		List<OrganizationBase> restOrganizationList = Lists.newArrayList(organizationBase)
		List<Organization> organizationList = Lists.newArrayList(organization)
		Page<Organization> organizationPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		OrganizationBaseResponse organizationResponseOutput = organizationRestReader.readBase(organizationRestBeanParams)

		then:
		1 * organizationRestQueryBuilderMock.query(organizationRestBeanParams) >> organizationPage
		1 * pageMapperMock.fromPageToRestResponsePage(organizationPage) >> responsePage
		1 * organizationRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * organizationPage.content >> organizationList
		1 * organizationBaseRestMapperMock.mapBase(organizationList) >> restOrganizationList
		0 * _
		organizationResponseOutput.organizations == restOrganizationList
		organizationResponseOutput.page == responsePage
		organizationResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		OrganizationFull organizationFull = Mock()
		Organization organization = Mock()
		List<Organization> organizationList = Lists.newArrayList(organization)
		Page<Organization> organizationPage = Mock()

		when:
		OrganizationFullResponse organizationResponseOutput = organizationRestReader.readFull(UID)

		then:
		1 * organizationRestQueryBuilderMock.query(_ as OrganizationRestBeanParams) >> { OrganizationRestBeanParams organizationRestBeanParams ->
			assert organizationRestBeanParams.uid == UID
			organizationPage
		}
		1 * organizationPage.content >> organizationList
		1 * organizationFullRestMapperMock.mapFull(organization) >> organizationFull
		0 * _
		organizationResponseOutput.organization == organizationFull
	}

	void "requires UID in full request"() {
		when:
		organizationRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
