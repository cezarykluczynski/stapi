package com.cezarykluczynski.stapi.etl.comicSeries.creation.processor

import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.util.AbstractComicSeriesTest
import com.google.common.collect.Sets

class ComicSeriesTemplateProcessorTest extends AbstractComicSeriesTest {

	private final Page page = Mock(Page)
	private final ComicSeries parentComicSeries = Mock(ComicSeries)

	private GuidGenerator guidGeneratorMock

	private ComicSeriesTemplateProcessor comicSeriesTemplateProcessor

	void setup() {
		guidGeneratorMock = Mock(GuidGenerator)
		comicSeriesTemplateProcessor = new ComicSeriesTemplateProcessor(guidGeneratorMock)
	}

	void "converts ComicSeriesTemplate to ComicSeries"() {
		given:
		Company publisher1 = Mock(Company)
		Company publisher2 = Mock(Company)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(
				page: page,
				title: TITLE,
				series: parentComicSeries,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedMonthFrom: PUBLISHED_MONTH_FROM,
				publishedDayFrom: PUBLISHED_DAY_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				publishedMonthTo: PUBLISHED_MONTH_TO,
				publishedDayTo: PUBLISHED_DAY_TO,
				numberOfIssues: NUMBER_OF_ISSUES,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				miniseries: MINISERIES,
				photonovelSeries: PHOTONOVEL_SERIES,
				publishers: Sets.newHashSet(publisher1, publisher2))

		when:
		ComicSeries comicSeries = comicSeriesTemplateProcessor.process(comicSeriesTemplate)

		then:
		1 * guidGeneratorMock.generateFromPage(page, ComicSeries) >> GUID
		0 * _
		comicSeries.guid == GUID
		comicSeries.page == page
		comicSeries.title == TITLE
		comicSeries.series == parentComicSeries
		comicSeries.publishedYearFrom == PUBLISHED_YEAR_FROM
		comicSeries.publishedMonthFrom == PUBLISHED_MONTH_FROM
		comicSeries.publishedDayFrom == PUBLISHED_DAY_FROM
		comicSeries.publishedYearTo == PUBLISHED_YEAR_TO
		comicSeries.publishedMonthTo == PUBLISHED_MONTH_TO
		comicSeries.publishedDayTo == PUBLISHED_DAY_TO
		comicSeries.numberOfIssues == NUMBER_OF_ISSUES
		comicSeries.stardateFrom == STARDATE_FROM
		comicSeries.stardateTo == STARDATE_TO
		comicSeries.yearFrom == YEAR_FROM
		comicSeries.yearTo == YEAR_TO
		comicSeries.miniseries == MINISERIES
		comicSeries.photonovelSeries == PHOTONOVEL_SERIES
		comicSeries.publishers.size() == 2
		comicSeries.publishers.contains publisher1
		comicSeries.publishers.contains publisher2
	}

}
