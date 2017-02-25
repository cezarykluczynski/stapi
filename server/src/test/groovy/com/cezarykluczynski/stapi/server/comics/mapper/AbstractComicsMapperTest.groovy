package com.cezarykluczynski.stapi.server.comics.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractComicsTest
import com.google.common.collect.Sets

abstract class AbstractComicsMapperTest extends AbstractComicsTest {

	protected Comics createComics() {
		new Comics(
				guid: GUID,
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
				comicSeries: Sets.newHashSet(Mock(ComicSeries)),
				writers: Sets.newHashSet(Mock(Staff)),
				artists: Sets.newHashSet(Mock(Staff)),
				editors: Sets.newHashSet(Mock(Staff)),
				staff: Sets.newHashSet(Mock(Staff)),
				characters: Sets.newHashSet(Mock(Character)),
				publishers: Sets.newHashSet(Mock(Company)),
				references: Sets.newHashSet(Mock(Reference)),
				comicCollections: Sets.newHashSet(Mock(ComicCollection)))
	}

}
