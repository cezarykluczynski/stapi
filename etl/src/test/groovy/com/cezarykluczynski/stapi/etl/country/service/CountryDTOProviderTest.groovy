package com.cezarykluczynski.stapi.etl.country.service

import com.google.common.collect.Sets
import spock.lang.Specification

class CountryDTOProviderTest extends Specification {

	private CountryDTOProvider countryDTOProvider

	void setup() {
		countryDTOProvider = new CountryDTOProvider()
	}

	void "should return set of CountryDTOs when string is provided"() {
		expect:
		output == countryDTOProvider.provideFromString(input)

		where:
		input                   | output
		''                      | Sets.newHashSet()
		'European'              | Sets.newHashSet()
		'Various'               | Sets.newHashSet()
		'United States'         | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('USA'))
		'Japan'                 | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('Japan'))
		'England'               | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('UK'))
		'United Kingdom'        | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('UK'))
		'Canada'                | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('Canada'))
		'Netherlands'           | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('Netherlands'))
		'Italy'                 | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('Italy'))
		'Australia'             | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('Australia'))
		'Germany'               | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('Germany'))
		'Australia New Zealand' | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('Australia'), CountryDTOProvider.NAME_INDEX.get('New Zealand'))
		'UK & US'               | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('USA'), CountryDTOProvider.NAME_INDEX.get('UK'))
		'United States Canada'  | Sets.newHashSet(CountryDTOProvider.NAME_INDEX.get('USA'), CountryDTOProvider.NAME_INDEX.get('Canada'))
	}

}
