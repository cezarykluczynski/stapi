package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl

import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

abstract class AbstractRealWorldPersonTest extends AbstractTest {

	protected static final String UID = 'ABCD0123456789'
	protected static final String NAME = 'NAME'
	protected static final String GENDER_STRING = 'GENDER_STRING'
	protected static final String BIRTH_NAME = 'BIRTH_NAME'
	protected static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	protected static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'
	protected static final LocalDate DATE_OF_BIRTH = LocalDate.of(1980, 1, 2)
	protected static final LocalDate DATE_OF_DEATH = LocalDate.of(2020, 3, 4)
	protected static final LocalDate DATE_OF_BIRTH_FROM = LocalDate.of(1960, 1, 1)
	protected static final LocalDate DATE_OF_BIRTH_TO = LocalDate.of(1970, 2, 2)
	protected static final LocalDate DATE_OF_DEATH_FROM = LocalDate.of(1980, 3, 3)
	protected static final LocalDate DATE_OF_DEATH_TO = LocalDate.of(1990, 4, 4)

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

	protected static final Boolean ANIMAL_PERFORMER = RandomUtil.nextBoolean()
	protected static final Boolean DIS_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean DS9_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean ENT_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean FILM_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean STAND_IN_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean STUNT_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean TAS_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean TNG_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean TOS_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean VIDEO_GAME_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean VOICE_PERFORMER =  RandomUtil.nextBoolean()
	protected static final Boolean VOY_PERFORMER =  RandomUtil.nextBoolean()

	protected static final boolean ART_DEPARTMENT = RandomUtil.nextBoolean()
	protected static final boolean ART_DIRECTOR = RandomUtil.nextBoolean()
	protected static final boolean PRODUCTION_DESIGNER = RandomUtil.nextBoolean()
	protected static final boolean CAMERA_AND_ELECTRICAL_DEPARTMENT = RandomUtil.nextBoolean()
	protected static final boolean CINEMATOGRAPHER = RandomUtil.nextBoolean()
	protected static final boolean CASTING_DEPARTMENT = RandomUtil.nextBoolean()
	protected static final boolean COSTUME_DEPARTMENT = RandomUtil.nextBoolean()
	protected static final boolean COSTUME_DESIGNER = RandomUtil.nextBoolean()
	protected static final boolean DIRECTOR = RandomUtil.nextBoolean()
	protected static final boolean ASSISTANT_AND_SECOND_UNIT_DIRECTOR = RandomUtil.nextBoolean()
	protected static final boolean EXHIBIT_AND_ATTRACTION_STAFF = RandomUtil.nextBoolean()
	protected static final boolean FILM_EDITOR = RandomUtil.nextBoolean()
	protected static final boolean LINGUIST = RandomUtil.nextBoolean()
	protected static final boolean LOCATION_STAFF = RandomUtil.nextBoolean()
	protected static final boolean MAKEUP_STAFF = RandomUtil.nextBoolean()
	protected static final boolean MUSIC_DEPARTMENT = RandomUtil.nextBoolean()
	protected static final boolean COMPOSER = RandomUtil.nextBoolean()
	protected static final boolean PERSONAL_ASSISTANT = RandomUtil.nextBoolean()
	protected static final boolean PRODUCER = RandomUtil.nextBoolean()
	protected static final boolean PRODUCTION_ASSOCIATE = RandomUtil.nextBoolean()
	protected static final boolean PRODUCTION_STAFF = RandomUtil.nextBoolean()
	protected static final boolean PUBLICATION_STAFF = RandomUtil.nextBoolean()
	protected static final boolean SCIENCE_CONSULTANT = RandomUtil.nextBoolean()
	protected static final boolean SOUND_DEPARTMENT = RandomUtil.nextBoolean()
	protected static final boolean SPECIAL_AND_VISUAL_EFFECTS_STAFF = RandomUtil.nextBoolean()
	protected static final boolean AUTHOR = RandomUtil.nextBoolean()
	protected static final boolean AUDIO_AUTHOR = RandomUtil.nextBoolean()
	protected static final boolean CALENDAR_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean COMIC_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean COMIC_AUTHOR = RandomUtil.nextBoolean()
	protected static final boolean COMIC_COLOR_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean COMIC_INTERIOR_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean COMIC_INK_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean COMIC_PENCIL_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean COMIC_LETTER_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean COMIC_STRIP_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean GAME_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean GAME_AUTHOR = RandomUtil.nextBoolean()
	protected static final boolean NOVEL_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean NOVEL_AUTHOR = RandomUtil.nextBoolean()
	protected static final boolean REFERENCE_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean REFERENCE_AUTHOR = RandomUtil.nextBoolean()
	protected static final boolean PUBLICATION_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean PUBLICATION_DESIGNER = RandomUtil.nextBoolean()
	protected static final boolean PUBLICATION_EDITOR = RandomUtil.nextBoolean()
	protected static final boolean PUBLICITY_ARTIST = RandomUtil.nextBoolean()
	protected static final boolean CBS_DIGITAL_STAFF = RandomUtil.nextBoolean()
	protected static final boolean ILM_PRODUCTION_STAFF = RandomUtil.nextBoolean()
	protected static final boolean SPECIAL_FEATURES_STAFF = RandomUtil.nextBoolean()
	protected static final boolean STORY_EDITOR = RandomUtil.nextBoolean()
	protected static final boolean STUDIO_EXECUTIVE = RandomUtil.nextBoolean()
	protected static final boolean STUNT_DEPARTMENT = RandomUtil.nextBoolean()
	protected static final boolean TRANSPORTATION_DEPARTMENT = RandomUtil.nextBoolean()
	protected static final boolean VIDEO_GAME_PRODUCTION_STAFF = RandomUtil.nextBoolean()
	protected static final boolean WRITER = RandomUtil.nextBoolean()

}
