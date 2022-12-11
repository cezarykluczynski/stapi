package com.cezarykluczynski.stapi.server.company.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2Base
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2Full
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2FullResponse
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.company.dto.CompanyV2RestBeanParams
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullRestMapper
import com.cezarykluczynski.stapi.server.company.query.CompanyRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class CompanyV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private CompanyRestQuery companyRestQueryBuilderMock

	private CompanyBaseRestMapper companyBaseRestMapperMock

	private CompanyFullRestMapper companyFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private CompanyV2RestReader companyV2RestReader

	void setup() {
		companyRestQueryBuilderMock = Mock()
		companyBaseRestMapperMock = Mock()
		companyFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		companyV2RestReader = new CompanyV2RestReader(companyRestQueryBuilderMock, companyBaseRestMapperMock, companyFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		CompanyV2Base companyV2Base = Mock()
		Company company = Mock()
		CompanyV2RestBeanParams companyV2RestBeanParams = Mock()
		List<CompanyV2Base> restCompanyList = Lists.newArrayList(companyV2Base)
		List<Company> companyList = Lists.newArrayList(company)
		Page<Company> companyPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		CompanyV2BaseResponse companyResponseOutput = companyV2RestReader.readBase(companyV2RestBeanParams)

		then:
		1 * companyRestQueryBuilderMock.query(companyV2RestBeanParams) >> companyPage
		1 * pageMapperMock.fromPageToRestResponsePage(companyPage) >> responsePage
		1 * companyV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * companyPage.content >> companyList
		1 * companyBaseRestMapperMock.mapV2Base(companyList) >> restCompanyList
		0 * _
		companyResponseOutput.companies == restCompanyList
		companyResponseOutput.page == responsePage
		companyResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		CompanyV2Full companyV2Full = Mock()
		Company company = Mock()
		List<Company> companyList = Lists.newArrayList(company)
		Page<Company> companyPage = Mock()

		when:
		CompanyV2FullResponse companyResponseOutput = companyV2RestReader.readFull(UID)

		then:
		1 * companyRestQueryBuilderMock.query(_ as CompanyV2RestBeanParams) >> { CompanyV2RestBeanParams companyV2RestBeanParams ->
			assert companyV2RestBeanParams.uid == UID
			companyPage
		}
		1 * companyPage.content >> companyList
		1 * companyFullRestMapperMock.mapV2Full(company) >> companyV2Full
		0 * _
		companyResponseOutput.company == companyV2Full
	}

	void "requires UID in full request"() {
		when:
		companyV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
