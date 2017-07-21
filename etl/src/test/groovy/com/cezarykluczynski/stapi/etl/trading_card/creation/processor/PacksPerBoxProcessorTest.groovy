package com.cezarykluczynski.stapi.etl.trading_card.creation.processor

import spock.lang.Specification
import spock.lang.Unroll

class PacksPerBoxProcessorTest extends Specification {

	private PacksPerBoxProcessor packsPerBoxProcessor

	void setup() {
		packsPerBoxProcessor = new PacksPerBoxProcessor()
	}

	@Unroll('when #input is passed, #output is returned')
	void "when string candidate is passed, packs per box is returned"() {
		expect:
		packsPerBoxProcessor.process(input) == output

		where:
		input                                         | output
		null                                          | null
		'Unknown'                                     | null
		'Unknown Unknown'                             | null
		'N/A'                                         | null
		'9'                                           | 9
		'15'                                          | 15
		'20'                                          | 20
		'42?'                                         | 42
		'50'                                          | 50
		'100'                                         | 100
		'36 24'                                       | 36
		'36 36'                                       | 36
		'40+1 CD'                                     | 40
		'20 + 1 Sound'                                | 20
		'20 + 1 Archive'                              | 20
		'18 Retail 36 Hobby'                          | 18
		'16 Walmart 18 Retail 24 Hobby'               | 18
		'12 Retail 36 Hobby'                          | 12
		'36 Hobby Box 18 Blockbuster Box'             | 18
		'36 (Regular) 36 (Blockbuster) 24 (Wal-Mart)' | 36
		'36 Hobby 20 Retail 24 Retail'                | 24
	}

}
