package com.cezarykluczynski.stapi.util

import spock.lang.Specification

import java.time.LocalDate

class AbstractSoundtrackTest extends Specification {

	protected static final String UID = 'UID'
	protected static final String TITLE = 'TITLE'
	protected static final LocalDate RELEASE_DATE = LocalDate.of(1990, 8, 4)
	protected static final LocalDate RELEASE_DATE_FROM = LocalDate.of(1991, 1, 2)
	protected static final LocalDate RELEASE_DATE_TO = LocalDate.of(1993, 3, 4)
	protected static final Integer LENGTH = 542

}
