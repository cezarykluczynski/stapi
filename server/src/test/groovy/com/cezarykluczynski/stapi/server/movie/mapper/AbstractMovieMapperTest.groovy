package com.cezarykluczynski.stapi.server.movie.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractMovieTest

abstract class AbstractMovieMapperTest extends AbstractMovieTest {

	protected Movie createMovie() {
		new Movie(
				uid: UID,
				mainDirector: new Staff(),
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
				writers: createSetOfRandomNumberOfMocks(Staff),
				screenplayAuthors: createSetOfRandomNumberOfMocks(Staff),
				storyAuthors: createSetOfRandomNumberOfMocks(Staff),
				directors: createSetOfRandomNumberOfMocks(Staff),
				producers: createSetOfRandomNumberOfMocks(Staff),
				performers: createSetOfRandomNumberOfMocks(Performer),
				staff: createSetOfRandomNumberOfMocks(Staff),
				stuntPerformers: createSetOfRandomNumberOfMocks(Performer),
				standInPerformers: createSetOfRandomNumberOfMocks(Performer),
				characters: createSetOfRandomNumberOfMocks(Character))
	}

}
