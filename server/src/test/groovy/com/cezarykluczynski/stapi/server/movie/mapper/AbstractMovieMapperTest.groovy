package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractMovieTest
import com.google.common.collect.Sets

abstract class AbstractMovieMapperTest extends AbstractMovieTest {

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
