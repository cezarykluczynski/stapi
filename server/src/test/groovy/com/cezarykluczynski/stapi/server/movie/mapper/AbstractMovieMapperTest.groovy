package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.google.common.collect.Sets
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl
import spock.lang.Specification

import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

abstract class AbstractMovieMapperTest extends Specification {

	protected static final String GUID = 'GUID'
	protected static final String TITLE = 'TITLE'
	protected static final String TITLE_BULGARIAN = 'TITLE_BULGARIAN'
	protected static final String TITLE_CATALAN = 'TITLE_CATALAN'
	protected static final String TITLE_CHINESE_TRADITIONAL = 'TITLE_CHINESE_TRADITIONAL'
	protected static final String TITLE_GERMAN = 'TITLE_GERMAN'
	protected static final String TITLE_ITALIAN = 'TITLE_ITALIAN'
	protected static final String TITLE_JAPANESE = 'TITLE_JAPANESE'
	protected static final String TITLE_POLISH = 'TITLE_POLISH'
	protected static final String TITLE_RUSSIAN = 'TITLE_RUSSIAN'
	protected static final String TITLE_SERBIAN = 'TITLE_SERBIAN'
	protected static final String TITLE_SPANISH = 'TITLE_SPANISH'
	protected static final Float STARDATE_FROM = 1514.2F
	protected static final Float STARDATE_TO = 1517.5F
	protected static final Integer YEAR_FROM = 2350
	protected static final Integer YEAR_TO = 2390
	protected static final LocalDate US_RELEASE_DATE = LocalDate.of(1990, 8, 4)
	protected static final LocalDate US_RELEASE_DATE_FROM = LocalDate.of(1991, 1, 2)
	protected static final LocalDate US_RELEASE_DATE_TO = LocalDate.of(1993, 3, 4)
	protected static final XMLGregorianCalendar US_RELEASE_DATE_XML = XMLGregorianCalendarImpl
			.createDate(1990, 8, 4, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar US_RELEASE_DATE_FROM_XML = XMLGregorianCalendarImpl
			.createDate(1991, 1, 2, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar US_RELEASE_DATE_TO_XML = XMLGregorianCalendarImpl
			.createDate(1993, 3, 4, DatatypeConstants.FIELD_UNDEFINED)

	protected Movie createMovie() {
		new Movie(
				guid: GUID,
				mainDirector: Mock(Staff),
				title: TITLE,
				titleBulgarian: TITLE_BULGARIAN,
				titleCatalan: TITLE_CATALAN,
				titleChineseTraditional: TITLE_CHINESE_TRADITIONAL,
				titleGerman: TITLE_GERMAN,
				titleItalian: TITLE_ITALIAN,
				titleJapanese: TITLE_JAPANESE,
				titlePolish: TITLE_POLISH,
				titleRussian: TITLE_RUSSIAN,
				titleSerbian: TITLE_SERBIAN,
				titleSpanish: TITLE_SPANISH,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				usReleaseDate: US_RELEASE_DATE,
				writers: Sets.newHashSet(Mock(Staff)),
				screenplayAuthors: Sets.newHashSet(Mock(Staff)),
				storyAuthors: Sets.newHashSet(Mock(Staff)),
				directors: Sets.newHashSet(Mock(Staff)),
				producers: Sets.newHashSet(Mock(Staff)),
				performers: Sets.newHashSet(Mock(Performer)),
				staff: Sets.newHashSet(Mock(Staff)),
				stuntPerformers: Sets.newHashSet(Mock(Performer)),
				standInPerformers: Sets.newHashSet(Mock(Performer)),
				characters: Sets.newHashSet(Mock(Character)))
	}

}
