package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyHeader
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CompanyHeaderRestMapperTest extends AbstractCompanyMapperTest {

	private CompanyHeaderRestMapper companyHeaderRestMapper

	void setup() {
		companyHeaderRestMapper = Mappers.getMapper(CompanyHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Company company = new Company(
				uid: UID,
				name: NAME)

		when:
		CompanyHeader companyHeader = companyHeaderRestMapper.map(Lists.newArrayList(company))[0]

		then:
		companyHeader.uid == UID
		companyHeader.name == NAME
	}

}
