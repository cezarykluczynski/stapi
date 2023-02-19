package com.cezarykluczynski.stapi.etl.template.book.processor

import spock.lang.Specification
import spock.lang.Unroll

class BookNumberOfPagesProcessorTest extends Specification {

	BookNumberOfPagesProcessor bookNumberOfPagesProcessor

	void setup() {
		bookNumberOfPagesProcessor = new BookNumberOfPagesProcessor()
	}

	@Unroll('when #value is passed, #result is returned')
	void "parses number of pages"() {
		expect:
		bookNumberOfPagesProcessor.process(value) == result

		where:
		value                                      | result
		null                                       | null
		''                                         | null
		' '                                        | null
		'\t'                                       | null
		'14 sheets'                                | null
		'13 prints, 16-page booklet'               | null
		'640'                                      | 640
		'234+'                                     | 234
		'1,120'                                    | 1120
		'11,300'                                   | 11300
		'12'                                       | 12
		'12 (1st edition)<br/>14 (Second Edition)' | 12
		' 58 (first edition)'                      | 58
		'240<br />160 (reprint)'                   | 240
		'464 (hardback)<br />384 (paperback)'      | 464
		'462; 432 (script pages only)'             | 462
		'178, plus foldouts'                       | 178
		'216, 224 (German)'                        | 216
		'48 &bull; Adventure Book (1st ed.)'       | 48
		'399 / 368'                                | 399
		'157 & unnumbered 8-page color insert'     | 157
	}

}
