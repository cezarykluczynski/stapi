package com.cezarykluczynski.stapi.etl.comicStrip.creation.processor

import com.cezarykluczynski.stapi.etl.template.comicStrip.dto.ComicStripTemplate
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.util.AbstractComicStripTest

class ComicStripTemplateProcessorTest extends AbstractComicStripTest {

	private final Page page = Mock(Page)

	private GuidGenerator guidGeneratorMock

	private ComicStripTemplateProcessor comicStripTemplateProcessor

	void setup() {
		guidGeneratorMock = Mock(GuidGenerator)
		comicStripTemplateProcessor = new ComicStripTemplateProcessor(guidGeneratorMock)
	}

	void "converts ComicStripTemplate to ComicStrip"() {
		given:
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate(
				page: page,
				title: TITLE,
				periodical: PERIODICAL,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedMonthFrom: PUBLISHED_MONTH_FROM,
				publishedDayFrom: PUBLISHED_DAY_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				publishedMonthTo: PUBLISHED_MONTH_TO,
				publishedDayTo: PUBLISHED_DAY_TO,
				numberOfPages: NUMBER_OF_PAGES,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO)

		when:
		ComicStrip comicStrip = comicStripTemplateProcessor.process(comicStripTemplate)

		then:
		1 * guidGeneratorMock.generateFromPage(page, ComicStrip) >> GUID
		comicStrip.guid == GUID
		comicStrip.page == page
		comicStrip.title == TITLE
		comicStrip.periodical == PERIODICAL
		comicStrip.publishedYearFrom == PUBLISHED_YEAR_FROM
		comicStrip.publishedMonthFrom == PUBLISHED_MONTH_FROM
		comicStrip.publishedDayFrom == PUBLISHED_DAY_FROM
		comicStrip.publishedYearTo == PUBLISHED_YEAR_TO
		comicStrip.publishedMonthTo == PUBLISHED_MONTH_TO
		comicStrip.publishedDayTo == PUBLISHED_DAY_TO
		comicStrip.numberOfPages == NUMBER_OF_PAGES
		comicStrip.stardateFrom == STARDATE_FROM
		comicStrip.stardateTo == STARDATE_TO
		comicStrip.yearFrom == YEAR_FROM
		comicStrip.yearTo == YEAR_TO
	}

}
