package com.cezarykluczynski.stapi.server.organization.reader

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBase
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFull
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullSoapMapper
import com.cezarykluczynski.stapi.server.organization.query.OrganizationSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class OrganizationSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private OrganizationSoapQuery organizationSoapQueryBuilderMock

	private OrganizationBaseSoapMapper organizationBaseSoapMapperMock

	private OrganizationFullSoapMapper organizationFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private OrganizationSoapReader organizationSoapReader

	void setup() {
		organizationSoapQueryBuilderMock = Mock()
		organizationBaseSoapMapperMock = Mock()
		organizationFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		organizationSoapReader = new OrganizationSoapReader(organizationSoapQueryBuilderMock, organizationBaseSoapMapperMock,
				organizationFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Organization> organizationList = Lists.newArrayList()
		Page<Organization> organizationPage = Mock()
		List<OrganizationBase> soapOrganizationList = Lists.newArrayList(new OrganizationBase(uid: UID))
		OrganizationBaseRequest organizationBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		OrganizationBaseResponse organizationResponse = organizationSoapReader.readBase(organizationBaseRequest)

		then:
		1 * organizationSoapQueryBuilderMock.query(organizationBaseRequest) >> organizationPage
		1 * organizationPage.content >> organizationList
		1 * pageMapperMock.fromPageToSoapResponsePage(organizationPage) >> responsePage
		1 * organizationBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * organizationBaseSoapMapperMock.mapBase(organizationList) >> soapOrganizationList
		0 * _
		organizationResponse.organizations[0].uid == UID
		organizationResponse.page == responsePage
		organizationResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		OrganizationFull organizationFull = new OrganizationFull(uid: UID)
		Organization organization = Mock()
		Page<Organization> organizationPage = Mock()
		OrganizationFullRequest organizationFullRequest = new OrganizationFullRequest(uid: UID)

		when:
		OrganizationFullResponse organizationFullResponse = organizationSoapReader.readFull(organizationFullRequest)

		then:
		1 * organizationSoapQueryBuilderMock.query(organizationFullRequest) >> organizationPage
		1 * organizationPage.content >> Lists.newArrayList(organization)
		1 * organizationFullSoapMapperMock.mapFull(organization) >> organizationFull
		0 * _
		organizationFullResponse.organization.uid == UID
	}

	void "requires UID in full request"() {
		given:
		OrganizationFullRequest organizationFullRequest = Mock()

		when:
		organizationSoapReader.readFull(organizationFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
