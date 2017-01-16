package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.template.individual.dto.DayMonthDTO
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Month

class DayMonthPageLinkProcessorTest extends Specification {

	private DayMonthPageLinkProcessor dayMonthPageLinkProcessor

	def setup() {
		dayMonthPageLinkProcessor = new DayMonthPageLinkProcessor()
	}

	@Unroll("when #pageLink is passed, #dayMonth is returned")
	def "when PageLink is passed, DayMonthDTO with parsed values is returned"() {
		expect:
		dayMonthPageLinkProcessor.process(pageLink) == dayMonth

		where:
		pageLink                                                    | dayMonth
		null                                                        | DayMonthDTO.of(null, null)
		new PageLink(title: 'January', description: 'January 16th') | DayMonthDTO.of(16, Month.JANUARY)
		new PageLink(title: 'Whatever', description: '2 February')  | DayMonthDTO.of(2, Month.FEBRUARY)
		new PageLink(title: 'Whatever', description: 'March 17')    | DayMonthDTO.of(17, Month.MARCH)
		new PageLink(title: null, description: null)                | DayMonthDTO.of(null, null)
		new PageLink(title: 'April', description: 'April 5')        | DayMonthDTO.of(5, Month.APRIL)
		new PageLink(title: 'May', description: '15 May')           | DayMonthDTO.of(15, Month.MAY)
		new PageLink(title: 'Whatever', description: 'June 3rd')    | DayMonthDTO.of(3, Month.JUNE)
	}

}
