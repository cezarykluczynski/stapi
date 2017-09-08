package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.individual.dto.DayMonthDTO
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.google.common.collect.Maps
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Month

class DayMonthPageLinkProcessorTest extends Specification {

	private MonthNameToMonthProcessor monthNameToMonthProcessorMock

	private DayMonthPageLinkProcessor dayMonthPageLinkProcessor

	void setup() {
		monthNameToMonthProcessorMock = Mock()
		dayMonthPageLinkProcessor = new DayMonthPageLinkProcessor(monthNameToMonthProcessorMock)
	}

	@Unroll('when #pageLink is passed, #dayMonth is returned')
	void "when PageLink is passed, DayMonthDTO with parsed values is returned"() {
		given:
		Map<String, Month> monthMap = Maps.newHashMap()
		monthMap['January'] = Month.JANUARY
		monthMap['February'] = Month.FEBRUARY
		monthMap['March'] = Month.MARCH
		monthMap['April'] = Month.APRIL
		monthMap['May'] = Month.MAY
		monthMap['June'] = Month.JUNE

		monthNameToMonthProcessorMock.process(_ as String) >> { String item ->
			monthMap.containsKey(item) ? monthMap.get(item) : null
		}

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
