package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.rest.model.Gender as GenderEnumRest
import com.cezarykluczynski.stapi.client.soap.DateRange
import com.cezarykluczynski.stapi.client.soap.GenderEnum as GenderEnumSoap
import com.cezarykluczynski.stapi.model.common.entity.Gender
import com.cezarykluczynski.stapi.util.tool.LogicUtil
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl
import spock.lang.Specification

import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

abstract class AbstractPerformerMapperTest extends Specification {

	protected static final String NAME = 'NAME'
	protected static final String BIRTH_NAME = 'BIRTH_NAME'

	protected static final GenderEnumSoap GENDER_ENUM_SOAP = GenderEnumSoap.F
	protected static final com.cezarykluczynski.stapi.client.rest.model.Gender GENDER_ENUM_REST = GenderEnumRest.F
	protected static final Gender GENDER = Gender.F

	protected static final Integer DATE_OF_BIRTH_FROM_YEAR = 1930
	protected static final Integer DATE_OF_BIRTH_FROM_MONTH = 4
	protected static final Integer DATE_OF_BIRTH_FROM_DAY = 13
	protected static final Integer DATE_OF_BIRTH_TO_YEAR = 1980
	protected static final Integer DATE_OF_BIRTH_TO_MONTH = 7
	protected static final Integer DATE_OF_BIRTH_TO_DAY = 22
	protected static final Integer DATE_OF_DEATH_FROM_YEAR = 2002
	protected static final Integer DATE_OF_DEATH_FROM_MONTH = 2
	protected static final Integer DATE_OF_DEATH_FROM_DAY = 6
	protected static final Integer DATE_OF_DEATH_TO_YEAR = 2015
	protected static final Integer DATE_OF_DEATH_TO_MONTH = 5
	protected static final Integer DATE_OF_DEATH_TO_DAY = 19
	protected static final XMLGregorianCalendar DATE_OF_BIRTH_FROM_SOAP = XMLGregorianCalendarImpl
			.createDate(DATE_OF_BIRTH_FROM_YEAR, DATE_OF_BIRTH_FROM_MONTH, DATE_OF_BIRTH_FROM_DAY, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar DATE_OF_BIRTH_TO_SOAP = XMLGregorianCalendarImpl
			.createDate(DATE_OF_BIRTH_TO_YEAR, DATE_OF_BIRTH_TO_MONTH, DATE_OF_BIRTH_TO_DAY, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar DATE_OF_DEATH_FROM_SOAP = XMLGregorianCalendarImpl
			.createDate(DATE_OF_DEATH_FROM_YEAR, DATE_OF_DEATH_FROM_MONTH, DATE_OF_DEATH_FROM_DAY, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar DATE_OF_DEATH_TO_SOAP = XMLGregorianCalendarImpl
			.createDate(DATE_OF_DEATH_TO_YEAR, DATE_OF_DEATH_TO_MONTH, DATE_OF_DEATH_TO_DAY, DatatypeConstants.FIELD_UNDEFINED)
	protected static final LocalDate DATE_OF_BIRTH_FROM_DB = LocalDate
			.of(DATE_OF_BIRTH_FROM_YEAR, DATE_OF_BIRTH_FROM_MONTH, DATE_OF_BIRTH_FROM_DAY)
	protected static final LocalDate DATE_OF_BIRTH_TO_DB = LocalDate
			.of(DATE_OF_BIRTH_TO_YEAR, DATE_OF_BIRTH_TO_MONTH, DATE_OF_BIRTH_TO_DAY)
	protected static final LocalDate DATE_OF_DEATH_FROM_DB = LocalDate
			.of(DATE_OF_DEATH_FROM_YEAR, DATE_OF_DEATH_FROM_MONTH, DATE_OF_DEATH_FROM_DAY)
	protected static final LocalDate DATE_OF_DEATH_TO_DB = LocalDate
			.of(DATE_OF_DEATH_TO_YEAR, DATE_OF_DEATH_TO_MONTH, DATE_OF_DEATH_TO_DAY)

	protected static final DateRange DATE_OF_BIRTH_SOAP = new DateRange(
			dateFrom: DATE_OF_BIRTH_FROM_SOAP,
			dateTo: DATE_OF_BIRTH_TO_SOAP)
	protected static final DateRange DATE_OF_DEATH_SOAP = new DateRange(
			dateFrom: DATE_OF_DEATH_FROM_SOAP,
			dateTo: DATE_OF_DEATH_TO_SOAP)

	protected static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	protected static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'

	protected static final Boolean ANIMAL_PERFORMER = LogicUtil.nextBoolean()
	protected static final Boolean DIS_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean DS9_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean ENT_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean FILM_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean STAND_IN_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean STUNT_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean TAS_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean TNG_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean TOS_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean VIDEO_GAME_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean VOICE_PERFORMER =  LogicUtil.nextBoolean()
	protected static final Boolean VOY_PERFORMER =  LogicUtil.nextBoolean()

}
