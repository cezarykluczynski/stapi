package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import spock.lang.Specification
import spock.lang.Unroll

class TextToDayMonthYearProcessorTest extends Specification {

	private TextToDayMonthYearProcessor textToDayMonthYearProcessor

	void setup() {
		textToDayMonthYearProcessor = new TextToDayMonthYearProcessor()
	}

	@Unroll('when #input is passed, #output is returned')
	void "when string candidate is passed, DayMonthYear is returned"() {
		expect:
		textToDayMonthYearProcessor.process(input) == output

		where:
		input                       | output
		null                        | null
		''                          | null
		'Various'                   | null

		'26 January 2011'           | DayMonthYear.of(26, 1, 2011)
		'14 February 1996'          | DayMonthYear.of(14, 2, 1996)
		'25 March 2003'             | DayMonthYear.of(25, 3, 2003)
		'24 April 2002'             | DayMonthYear.of(24, 4, 2002)
		'1 May 2017'                | DayMonthYear.of(1, 5, 2017)
		'21 June 2006'              | DayMonthYear.of(21, 6, 2006)
		'27 July 2016'              | DayMonthYear.of(27, 7, 2016)
		'16 August 1995'            | DayMonthYear.of(16, 8, 1995)
		'22 September 1999'         | DayMonthYear.of(22, 9, 1999)
		'2 October 2000'            | DayMonthYear.of(2, 10, 2000)
		'20 November 2002'          | DayMonthYear.of(20, 11, 2002)
		'10 December 2008'          | DayMonthYear.of(10, 12, 2008)

		'12 Jan 2011'               | DayMonthYear.of(12, 1, 2011)
		'5 Feb 1996'                | DayMonthYear.of(5, 2, 1996)
		'22 Mar 2003'               | DayMonthYear.of(22, 3, 2003)
		'15 Apr 2002'               | DayMonthYear.of(15, 4, 2002)
		'8 May 2017'                | DayMonthYear.of(8, 5, 2017)
		'30 Jun 2006'               | DayMonthYear.of(30, 6, 2006)
		'12 Jul 2016'               | DayMonthYear.of(12, 7, 2016)
		'7 Aug 1993'                | DayMonthYear.of(7, 8, 1993)
		'9 Sep 1994'                | DayMonthYear.of(9, 9, 1994)
		'4 Oct 2000'                | DayMonthYear.of(4, 10, 2000)
		'16 Nov 2011'               | DayMonthYear.of(16, 11, 2011)
		'21 Dec 2001'               | DayMonthYear.of(21, 12, 2001)

		'January 1992'              | DayMonthYear.of(null, 1, 1992)
		'February 2007'             | DayMonthYear.of(null, 2, 2007)
		'March 2012'                | DayMonthYear.of(null, 3, 2012)
		'April 2009'                | DayMonthYear.of(null, 4, 2009)
		'May 1994'                  | DayMonthYear.of(null, 5, 1994)
		'June 1996'                 | DayMonthYear.of(null, 6, 1996)
		'July 1991'                 | DayMonthYear.of(null, 7, 1991)
		'August 1991'               | DayMonthYear.of(null, 8, 1991)
		'September 1998'            | DayMonthYear.of(null, 9, 1998)
		'October 1991'              | DayMonthYear.of(null, 10, 1991)
		'November 1996'             | DayMonthYear.of(null, 11, 1996)
		'December 1994'             | DayMonthYear.of(null, 12, 1994)

		'Jan 1994'                  | DayMonthYear.of(null, 1, 1994)
		'Feb 2001'                  | DayMonthYear.of(null, 2, 2001)
		'Mar 2015'                  | DayMonthYear.of(null, 3, 2015)
		'Apr 2004'                  | DayMonthYear.of(null, 4, 2004)
		'May 1999'                  | DayMonthYear.of(null, 5, 1999)
		'Jun 1993'                  | DayMonthYear.of(null, 6, 1993)
		'Jul 1995'                  | DayMonthYear.of(null, 7, 1995)
		'Aug 1998'                  | DayMonthYear.of(null, 8, 1998)
		'Sep 1992'                  | DayMonthYear.of(null, 9, 1992)
		'Oct 1994'                  | DayMonthYear.of(null, 10, 1994)
		'Nov 1997'                  | DayMonthYear.of(null, 11, 1997)
		'Dec 1991'                  | DayMonthYear.of(null, 12, 1991)

		'1969'                      | DayMonthYear.of(null, null, 1969)
		'1974'                      | DayMonthYear.of(null, null, 1974)
		'1986'                      | DayMonthYear.of(null, null, 1986)
		'1998'                      | DayMonthYear.of(null, null, 1998)
		'2002'                      | DayMonthYear.of(null, null, 2002)
		'2018'                      | DayMonthYear.of(null, null, 2018)

		'1976 1976'                 | DayMonthYear.of(null, null, 1976)
		'1995-1996'                 | DayMonthYear.of(null, null, 1995)
		'1970 or 1971'              | DayMonthYear.of(null, null, 1970)
		'1967 (1989)'               | DayMonthYear.of(null, null, 1967)
		'1995 – 1997'               | DayMonthYear.of(null, null, 1995)
		'2003-Current'              | DayMonthYear.of(null, null, 2003)
		'2000-2002 UK 2011-2013 US' | DayMonthYear.of(null, null, 2000)
	}

}
