package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CompanyHeader
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CompanyHeaderSoapMapperTest extends AbstractCompanyMapperTest {

	private CompanyHeaderSoapMapper companyHeaderSoapMapper

	void setup() {
		companyHeaderSoapMapper = Mappers.getMapper(CompanyHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Company company = new Company(
				uid: UID,
				name: NAME)

		when:
		CompanyHeader companyHeader = companyHeaderSoapMapper.map(Lists.newArrayList(company))[0]

		then:
		companyHeader.uid == UID
		companyHeader.name == NAME
	}

}
