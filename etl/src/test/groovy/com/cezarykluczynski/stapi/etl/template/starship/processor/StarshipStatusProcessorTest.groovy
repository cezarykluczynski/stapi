package com.cezarykluczynski.stapi.etl.template.starship.processor

import spock.lang.Specification
import spock.lang.Unroll

class StarshipStatusProcessorTest extends Specification {

	private StarshipStatusProcessor starshipStatusProcessor

	void setup() {
		starshipStatusProcessor = new StarshipStatusProcessor()
	}

	@Unroll('when #input is passed, #output is returned')
	void "when string candidate is passed, starship status is returned"() {
		expect:
		starshipStatusProcessor.process(input) == output

		where:
		input                                                                                                   | output
		null                                                                                                    | null
		''                                                                                                      | null
		'Presumably destroyed or assimilated'                                                                   | null
		'''Presumed destroyed'''                                                                                | null
		'Presumably destroyed'                                                                                  | null
		'Unknown'                                                                                               | null
		'Presumed destroyed'                                                                                    | null
		'Adrift'                                                                                                | 'Adrift'
		'Cannibalized'                                                                                          | 'Cannibalized'
		'assimilated'                                                                                           | 'Assimilated'
		'Damaged'                                                                                               | 'Damaged'
		'Captured'                                                                                              | 'Captured'
		'Both ships disabled'                                                                                   | 'Disabled'
		'Destroyed'                                                                                             | 'Destroyed'
		'Decommissioned'                                                                                        | 'Decommissioned'
		'Scuttled'                                                                                              | 'Scuttled'
		'Heavily damaged'                                                                                       | 'Damaged'
		'Salvaged'                                                                                              | 'Salvaged'
		'Sunk'                                                                                                  | 'Sunk'
		'Recovered'                                                                                             | 'Recovered'
		'Disabled'                                                                                              | 'Disabled'
		'Wrecked'                                                                                               | 'Wrecked'
		'Abandoned on [[Lambda Paz]]'                                                                           | 'Abandoned'
		'Derelict'                                                                                              | 'Derelict'
		'Captured by Terrans'                                                                                   | 'Captured'
		'Active'                                                                                                | 'Active'
		'Abandoned'                                                                                             | 'Abandoned'
		'Missing'                                                                                               | 'Missing'
		'Planned'                                                                                               | 'Planned'
		'''Missing ([[prime reality]]) <small>''([[2164]])''</small><br />Wrecked ([[alternate reality]])'''    | 'Missing'
		'''Adrift ([[universe|prime reality]]) ''([[2267]])''<br />Adrift ([[alternate reality]])'''            | 'Adrift'
		''''Active ([[prime reality]])  <small>''([[2233]])''</small><br />Destroyed ([[alternate reality]])''' | 'Active'
		'''Missing ([[prime reality]]) <small>''([[2387]])''</small><br />Destroyed ([[alternate reality]])'''  | 'Missing'
		'''Missing ([[prime universe]]) <small>''([[2268]])''</small><br />Active ([[mirror universe]])'''      | 'Missing'
	}

}
