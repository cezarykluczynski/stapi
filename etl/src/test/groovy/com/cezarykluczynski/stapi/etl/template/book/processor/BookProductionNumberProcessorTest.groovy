package com.cezarykluczynski.stapi.etl.template.book.processor

import spock.lang.Specification
import spock.lang.Unroll

class BookProductionNumberProcessorTest extends Specification {

	BookProductionNumberProcessor bookProductionNumberProcessor

	void setup() {
		bookProductionNumberProcessor = new BookProductionNumberProcessor()
	}

	@Unroll('when #value is passed, #result is returned')
	void "parses book production number"() {
		expect:
		bookProductionNumberProcessor.process(value) == result

		where:
		value                    | result
		null                     | null
		'FBC123'                 | 'FBC123'
		'AA14 \nAB15'            | 'AA14'
		'2003 (eBook)<br>\n2005' | '2003'
	}

}
