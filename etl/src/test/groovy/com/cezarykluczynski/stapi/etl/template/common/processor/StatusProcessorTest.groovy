package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApiImpl
import spock.lang.Specification
import spock.lang.Unroll

class StatusProcessorTest extends Specification {

	private WikitextApi wikitextApiMock

	private StatusProcessor statusProcessor

	void setup() {
		wikitextApiMock = Mock()
		statusProcessor = new StatusProcessor(new WikitextApiImpl())
	}

	@Unroll('when #input is passed, #output is returned')
	void "when string candidate is passed, starship status is returned"() {
		expect:
		statusProcessor.process(input) == output

		where:
		input                                                                                                   | output
		null                                                                                                    | null
		''                                                                                                      | null
		// starship
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
		'[[Museum ship]] at [[Athan Prime]]'                                                                    | 'Decommissioned'
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
		'[[Crash]]ed'                                                                                           | 'Crashed'
		// hologram
		'Dematerialized'                                                                                        | 'Dematerialized'
		'Detained in external memory module'                                                                    | 'Detained'
		'Detained in external memory module ([[2369]])'                                                         | 'Detained'
		'Deactivation'                                                                                          | 'Deactivation'
		'Deceased'                                                                                              | 'Deceased'
		'Active'                                                                                                | 'Active'
		'Deactivated ([[2377]])'                                                                                | 'Deactivated'
		'Active ([[2377]])'                                                                                     | 'Active'
		'Deactivated'                                                                                           | 'Deactivated'
	}

}
