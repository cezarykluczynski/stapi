package com.cezarykluczynski.stapi.etl.comics.creation.processor

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractComicsTest
import com.google.common.collect.Sets

class ComicsTemplateProcessorTest extends AbstractComicsTest {

	private UidGenerator uidGeneratorMock

	private ComicsTemplateProcessor comicsTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		comicsTemplateProcessor = new ComicsTemplateProcessor(uidGeneratorMock)
	}

	void "converts ComicsTemplate to Comics"() {
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
				adaptation: ADAPTATION,
				comicSeries: Sets.newHashSet(comicSeries1, comicSeries2),
				writers: Sets.newHashSet(writer1, writer2),
				artists: Sets.newHashSet(artist1, artist2),
				editors: Sets.newHashSet(editor1, editor2),
				staff: Sets.newHashSet(staff1, staff2),
				publishers: Sets.newHashSet(publisher1, publisher2),
				characters: Sets.newHashSet(character1, character2),
				references: Sets.newHashSet(reference1, reference2))

		when:
		Comics comics = comicsTemplateProcessor.process(comicsTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, Comics) >> UID
		0 * _
		comics.uid == UID
		comics.page == page
		comics.title == TITLE
		comics.publishedYear == PUBLISHED_YEAR
		comics.publishedMonth == PUBLISHED_MONTH
		comics.publishedDay == PUBLISHED_DAY
		comics.coverYear == COVER_YEAR
		comics.coverMonth == COVER_MONTH
		comics.coverDay == COVER_DAY
		comics.numberOfPages == NUMBER_OF_PAGES
		comics.stardateFrom == STARDATE_FROM
		comics.stardateTo == STARDATE_TO
		comics.yearFrom == YEAR_FROM
		comics.yearTo == YEAR_TO
		comics.photonovel == PHOTONOVEL
		comics.adaptation == ADAPTATION
		comics.comicSeries.contains comicSeries1
		comics.comicSeries.contains comicSeries2
		comics.writers.contains writer1
		comics.writers.contains writer2
		comics.artists.contains artist1
		comics.artists.contains artist1
		comics.editors.contains editor1
		comics.editors.contains editor2
		comics.staff.contains staff1
		comics.staff.contains staff2
		comics.publishers.contains publisher1
		comics.publishers.contains publisher2
		comics.characters.contains character1
		comics.characters.contains character2
		comics.references.contains reference1
		comics.references.contains reference2
	}

}
