package com.cezarykluczynski.stapi.server.comic_collection.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractComicCollectionTest

abstract class AbstractComicCollectionMapperTest extends AbstractComicCollectionTest {

	protected ComicCollection createComicCollection() {
		new ComicCollection(
				uid: UID,
				title: TITLE,
				publishedYear: PUBLISHED_YEAR,
				publishedMonth: PUBLISHED_MONTH,
				publishedDay: PUBLISHED_DAY,
				coverYear: COVER_YEAR,
				coverMonth: COVER_MONTH,
				coverDay: COVER_DAY,
				numberOfPages: NUMBER_OF_PAGES,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				photonovel: PHOTONOVEL,
				comicSeries: createSetOfRandomNumberOfMocks(ComicSeries),
				writers: createSetOfRandomNumberOfMocks(Staff),
				artists: createSetOfRandomNumberOfMocks(Staff),
				editors: createSetOfRandomNumberOfMocks(Staff),
				staff: createSetOfRandomNumberOfMocks(Staff),
				characters: createSetOfRandomNumberOfMocks(Character),
				publishers: createSetOfRandomNumberOfMocks(Company),
				references: createSetOfRandomNumberOfMocks(Reference),
				comics: createSetOfRandomNumberOfMocks(Comics))
	}

}
