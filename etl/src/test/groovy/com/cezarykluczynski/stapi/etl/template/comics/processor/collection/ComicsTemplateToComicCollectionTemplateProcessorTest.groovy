package com.cezarykluczynski.stapi.etl.template.comics.processor.collection

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionTemplate
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractComicCollectionTest
import com.google.common.collect.Sets

class ComicsTemplateToComicCollectionTemplateProcessorTest extends AbstractComicCollectionTest {

	private final Page page = Mock()

	private ComicsTemplateToComicCollectionTemplateProcessor collectionTemplateProcessor

	void setup() {
		collectionTemplateProcessor = new ComicsTemplateToComicCollectionTemplateProcessor()
	}

	void "converts ComicsTemplate to ComicCollectionTemplate"() {
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

		ComicsTemplate comicsTemplate = new ComicsTemplate(
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
				references: Sets.newHashSet(reference1, reference2))

		when:
		ComicCollectionTemplate comicCollectionTemplate = collectionTemplateProcessor.process(comicsTemplate)

		then:
		comicCollectionTemplate.page == page
		comicCollectionTemplate.title == TITLE
		comicCollectionTemplate.publishedYear == PUBLISHED_YEAR
		comicCollectionTemplate.publishedMonth == PUBLISHED_MONTH
		comicCollectionTemplate.publishedDay == PUBLISHED_DAY
		comicCollectionTemplate.coverYear == COVER_YEAR
		comicCollectionTemplate.coverMonth == COVER_MONTH
		comicCollectionTemplate.coverDay == COVER_DAY
		comicCollectionTemplate.numberOfPages == NUMBER_OF_PAGES
		comicCollectionTemplate.stardateFrom == STARDATE_FROM
		comicCollectionTemplate.stardateTo == STARDATE_TO
		comicCollectionTemplate.yearFrom == YEAR_FROM
		comicCollectionTemplate.yearTo == YEAR_TO
		comicCollectionTemplate.photonovel == PHOTONOVEL
		comicCollectionTemplate.comicSeries.contains comicSeries1
		comicCollectionTemplate.comicSeries.contains comicSeries2
		comicCollectionTemplate.writers.contains writer1
		comicCollectionTemplate.writers.contains writer2
		comicCollectionTemplate.artists.contains artist1
		comicCollectionTemplate.artists.contains artist1
		comicCollectionTemplate.editors.contains editor1
		comicCollectionTemplate.editors.contains editor2
		comicCollectionTemplate.staff.contains staff1
		comicCollectionTemplate.staff.contains staff2
		comicCollectionTemplate.publishers.contains publisher1
		comicCollectionTemplate.publishers.contains publisher2
		comicCollectionTemplate.characters.contains character1
		comicCollectionTemplate.characters.contains character2
		comicCollectionTemplate.references.contains reference1
		comicCollectionTemplate.references.contains reference2
	}

}
