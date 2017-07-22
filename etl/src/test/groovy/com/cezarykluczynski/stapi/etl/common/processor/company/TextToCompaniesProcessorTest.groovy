package com.cezarykluczynski.stapi.etl.common.processor.company

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.service.CompanyAliasFixedValueProvider
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class TextToCompaniesProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String NAME_2 = 'NAME_2'
	private static final String NAME_ALIAS = 'NAME_ALIAS'
	private static final String NAME_COMPOSITE = "${NAME} ${NAME_2}"

	private CompanyRepository companyRepositoryMock

	private CompanyAliasFixedValueProvider companyAliasFixedValueProviderMock

	private TextToCompaniesProcessor textToCompaniesProcessor

	void setup() {
		companyRepositoryMock = Mock()
		companyAliasFixedValueProviderMock = Mock()
		textToCompaniesProcessor = new TextToCompaniesProcessor(companyRepositoryMock, companyAliasFixedValueProviderMock)
	}

	void "when passed string is among invalid titles, empty set is returned"() {
		when:
		Set<Company> companySet = textToCompaniesProcessor.process(RandomUtil.randomItem(TextToCompaniesProcessor.INVALID_NAMES))

		then:
		0 * _
		companySet.empty
	}

	void "when company is found in repository, it is used"() {
		given:
		Company company = Mock()

		when:
		Set<Company> companySet = textToCompaniesProcessor.process(NAME)

		then:
		1 * companyAliasFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * companyRepositoryMock.findByName(NAME) >> Optional.of(company)
		0 * _
		companySet.size() == 1
		companySet.contains company
	}

	void "when company is not found in repository, and no spaces are in passed string, empty set is returned"() {
		when:
		Set<Company> companySet = textToCompaniesProcessor.process(NAME)

		then:
		1 * companyAliasFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * companyRepositoryMock.findByName(NAME) >> Optional.empty()
		0 * _
		companySet.empty
	}

	void "when CompanyAliasFixedValueProvider finds alias, it is used to query repository"() {
		given:
		Company company = Mock()

		when:
		Set<Company> companySet = textToCompaniesProcessor.process(NAME)

		then:
		1 * companyAliasFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.found(NAME_ALIAS)
		1 * companyRepositoryMock.findByName(NAME_ALIAS) >> Optional.of(company)
		0 * _
		companySet.size() == 1
		companySet.contains company
	}

	void "when CompanyAliasFixedValueProvider finds alias, but repository does not find anything, empty set is returned"() {
		when:
		Set<Company> companySet = textToCompaniesProcessor.process(NAME)

		then:
		1 * companyAliasFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.found(NAME_ALIAS)
		1 * companyRepositoryMock.findByName(NAME_ALIAS) >> Optional.empty()
		0 * _
		companySet.empty
	}

	void "when company cannot be found by full name, name parts are used"() {
		given:
		Company company1 = Mock()
		Company company2 = Mock()

		when:
		Set<Company> companySet = textToCompaniesProcessor.process(NAME_COMPOSITE)

		then:
		1 * companyAliasFixedValueProviderMock.getSearchedValue(NAME_COMPOSITE) >> FixedValueHolder.empty()
		1 * companyRepositoryMock.findByName(NAME_COMPOSITE) >> Optional.empty()
		1 * companyAliasFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.found(NAME_ALIAS)
		1 * companyRepositoryMock.findByName(NAME_ALIAS) >> Optional.of(company1)
		1 * companyAliasFixedValueProviderMock.getSearchedValue(NAME_2) >> FixedValueHolder.empty()
		1 * companyRepositoryMock.findByName(NAME_2) >> Optional.of(company2)
		1 *
		0 * _
		companySet.size() == 2
		companySet.contains company1
		companySet.contains company2
	}

}
