package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import spock.lang.Specification
import spock.lang.Unroll

class PageLinkToYearProcessorTest extends Specification {

	private PageLinkToYearProcessor pageLinkToYearProcessor

	void setup() {
		pageLinkToYearProcessor = new PageLinkToYearProcessor()
	}

	@Unroll('#output is returned when #input is passed')
	void "valid input produces not null output, invalid input produces null output"() {
		expect:
		pageLinkToYearProcessor.process(input) == output

		where:
		input                                             | output
		null                                              | null
		new PageLink(title: '2010s')                      | null
		new PageLink(title: 'Foo', description: '2010s')  | null
		new PageLink(title: '2010')                       | 2010
		new PageLink(title: '2000', description: '2010')  | 2000
		new PageLink(title: '2000', description: '2010s') | 2000
		new PageLink(title: 'Foo', description: '2010')   | 2010
	}

}
