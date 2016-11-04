package com.cezarykluczynski.stapi.util

import spock.lang.Specification

import java.time.LocalDate

abstract class AbstractRealWorldPersonTest extends Specification {

	protected static final String NAME = 'NAME'
	protected static final String BIRTH_NAME = 'BIRTH_NAME'
	protected static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	protected static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'
	protected static final LocalDate DATE_OF_BIRTH = LocalDate.of(1980, 1, 2)
	protected static final LocalDate DATE_OF_DEATH = LocalDate.of(2020, 3, 4)
	protected static final LocalDate DATE_OF_BIRTH_FROM = LocalDate.of(1960, 1, 1)
	protected static final LocalDate DATE_OF_BIRTH_TO = LocalDate.of(1970, 2, 2)
	protected static final LocalDate DATE_OF_DEATH_FROM = LocalDate.of(1980, 3, 3)
	protected static final LocalDate DATE_OF_DEATH_TO = LocalDate.of(1990, 4, 4)

}
