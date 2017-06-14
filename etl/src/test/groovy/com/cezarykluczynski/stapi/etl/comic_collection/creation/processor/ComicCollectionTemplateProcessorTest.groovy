package com.cezarykluczynski.stapi.etl.comic_collection.creation.processor

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractComicCollectionTest
import com.google.common.collect.Sets

class ComicCollectionTemplateProcessorTest extends AbstractComicCollectionTest {

	private UidGenerator uidGeneratorMock

	private ComicCollectionTemplateProcessor comicCollectionTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		comicCollectionTemplateProcessor = new ComicCollectionTemplateProcessor(uidGeneratorMock)
	}

	void "converts ComicCollectionTemplate to ComicCollection"() {
		given:
		ComicSeries comicSeries1 = Mock()
		ComicSeries comicSeries2 = Mock()
		Staff writer1 = Mock()
		Staff writer2 = Mock()
		Staff artist1 = Mock()
		Staff artist2 = Mock()
		Staff editor1 = Mock()
		Staff editor2 = Mock()
		Staff staff1 = Mock()
		Staff staff2 = Mock()
		Company publisher1 = Mock()
		Company publisher2 = Mock()
		Character character1 = Mock()
		Character character2 = Mock()
		Reference reference1 = Mock()
		Reference reference2 = Mock()
		Comics comics1 = Mock()
		Comics comics2 = Mock()

		ComicCollectionTemplate comicCollectionTemplate = new ComicCollectionTemplate(
				page: page,
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
				comicSeries: Sets.newHashSet(comicSeries1, comicSeries2),
				writers: Sets.newHashSet(writer1, writer2),
				artists: Sets.newHashSet(artist1, artist2),
				editors: Sets.newHashSet(editor1, editor2),
				staff: Sets.newHashSet(staff1, staff2),
				publishers: Sets.newHashSet(publisher1, publisher2),
				characters: Sets.newHashSet(character1, character2),
				references: Sets.newHashSet(reference1, reference2),
				comics: Sets.newHashSet(comics1, comics2))

		when:
		ComicCollection comicCollection = comicCollectionTemplateProcessor.process(comicCollectionTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, ComicCollection) >> UID
		0 * _
		comicCollection.uid == UID
		comicCollection.page == page
		comicCollection.title == TITLE
		comicCollection.publishedYear == PUBLISHED_YEAR
		comicCollection.publishedMonth == PUBLISHED_MONTH
		comicCollection.publishedDay == PUBLISHED_DAY
		comicCollection.coverYear == COVER_YEAR
		comicCollection.coverMonth == COVER_MONTH
		comicCollection.coverDay == COVER_DAY
		comicCollection.numberOfPages == NUMBER_OF_PAGES
		comicCollection.stardateFrom == STARDATE_FROM
		comicCollection.stardateTo == STARDATE_TO
		comicCollection.yearFrom == YEAR_FROM
		comicCollection.yearTo == YEAR_TO
		comicCollection.photonovel == PHOTONOVEL
		comicCollection.comicSeries.contains comicSeries1
		comicCollection.comicSeries.contains comicSeries2
		comicCollection.writers.contains writer1
		comicCollection.writers.contains writer2
		comicCollection.artists.contains artist1
		comicCollection.artists.contains artist1
		comicCollection.editors.contains editor1
		comicCollection.editors.contains editor2
		comicCollection.staff.contains staff1
		comicCollection.staff.contains staff2
		comicCollection.publishers.contains publisher1
		comicCollection.publishers.contains publisher2
		comicCollection.characters.contains character1
		comicCollection.characters.contains character2
		comicCollection.references.contains reference1
		comicCollection.references.contains reference2
		comicCollection.comics.contains comics1
		comicCollection.comics.contains comics2
	}

}
