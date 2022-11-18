package com.cezarykluczynski.stapi.etl.template.book.processor

import spock.lang.Specification
import spock.lang.Unroll

class BookNumberOfPagesProcessorTest extends Specification {

	BookProductionNumberProcessor bookProductionNumberProcessor

	void setup() {
		bookProductionNumberProcessor = new BookProductionNumberProcessor()
	}

	@Unroll('when #value is passed, #result is returned')
	void "parses number of pages"() {
		given:
		bookProductionNumberProcessor.process(value) == result

		where:
		value                                      | result
		null                                       | null
		''                                         | null
		' '                                        | null
		'\t'                                       | null
		'640'                                      | 640
		'12'                                       | 12
		'12 (1st edition)<br/>14 (Second Edition)' | null
	}

}
