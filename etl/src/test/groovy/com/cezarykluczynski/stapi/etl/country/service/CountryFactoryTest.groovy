package com.cezarykluczynski.stapi.etl.country.service

import com.cezarykluczynski.stapi.etl.country.dto.CountryDTO
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.country.entity.Country
import com.cezarykluczynski.stapi.model.country.repository.CountryRepository
import spock.lang.Specification

class CountryFactoryTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String CODE = 'CODE'
	private static final String UID = 'UID'

	private CountryRepository countryRepositoryMock

	private UidGenerator uidGeneratorMock

	private CountryFactory countryFactory

	void setup() {
		countryRepositoryMock = Mock()
		uidGeneratorMock = Mock()
		countryFactory = new CountryFactory(countryRepositoryMock, uidGeneratorMock)
	}

	void "returns existing entity when it can be found"() {
		given:
		Country country = Mock()
		CountryDTO countryDTO = CountryDTO.of(NAME, CODE)

		when:
		Country countryOutput = countryFactory.create(countryDTO)

		then:
		1 * countryRepositoryMock.findByIso31661Alpha2Code(CODE) >> Optional.of(country)
		0 * _
		countryOutput == country
	}

	void "saves new entity, then returns it, when country cannot be found by code"() {
		given:
		CountryDTO countryDTO = CountryDTO.of(NAME, CODE)

		when:
		Country country = countryFactory.create(countryDTO)

		then:
		1 * countryRepositoryMock.findByIso31661Alpha2Code(CODE) >> Optional.empty()
		1 * countryRepositoryMock.save(_ as Country) >> { Country countryInput ->
			assert countryInput.name == NAME
			assert countryInput.iso31661Alpha2Code == CODE
			assert countryInput.uid == UID
			countryInput
		}
		1 * uidGeneratorMock.generateForCountry(CODE) >> UID
		0 * _
		country.name == NAME
		country.iso31661Alpha2Code == CODE
		country.uid == UID
	}

}
