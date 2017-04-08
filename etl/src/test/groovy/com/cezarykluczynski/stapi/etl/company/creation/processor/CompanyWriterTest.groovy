package com.cezarykluczynski.stapi.etl.company.creation.processor

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class CompanyWriterTest extends Specification {

	private CompanyRepository companyRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private CompanyWriter companyWriterMock

	void setup() {
		companyRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		companyWriterMock = new CompanyWriter(companyRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Company company = new Company()
		List<Company> companyList = Lists.newArrayList(company)

		when:
		companyWriterMock.write(companyList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Company) >> { args ->
			assert args[0][0] == company
			companyList
		}
		1 * companyRepositoryMock.save(companyList)
		0 * _
	}

}
