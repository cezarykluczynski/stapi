package com.cezarykluczynski.stapi.etl.company.creation.processor

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class CompanyWriterTest extends Specification {

	private CompanyRepository companyRepositoryMock

	private CompanyWriter companyWriterMock

	void setup() {
		companyRepositoryMock = Mock()
		companyWriterMock = new CompanyWriter(companyRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Company company = new Company()
		List<Company> companyList = Lists.newArrayList(company)

		when:
		companyWriterMock.write(new Chunk(companyList))

		then:
		1 * companyRepositoryMock.saveAll(companyList)
		0 * _
	}

}
