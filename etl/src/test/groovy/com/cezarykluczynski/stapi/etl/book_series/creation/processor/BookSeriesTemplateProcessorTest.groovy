package com.cezarykluczynski.stapi.etl.book_series.creation.processor

import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.util.AbstractBookSeriesTest
import com.google.common.collect.Sets

class BookSeriesTemplateProcessorTest extends AbstractBookSeriesTest {

	private final Page page = Mock()

	private UidGenerator uidGeneratorMock

	private BookSeriesTemplateProcessor bookSeriesTemplateProcessor

	void setup() {
		uidGeneratorMock = Mock()
		bookSeriesTemplateProcessor = new BookSeriesTemplateProcessor(uidGeneratorMock)
	}

	void "converts BookSeriesTemplate to BookSeries"() {
		given:
		Company publisher1 = Mock()
		Company publisher2 = Mock()
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate(
				page: page,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedMonthFrom: PUBLISHED_MONTH_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				publishedMonthTo: PUBLISHED_MONTH_TO,
				numberOfBooks: NUMBER_OF_BOOKS,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				miniseries: MINISERIES,
				eBookSeries: E_BOOK_SERIES,
				publishers: Sets.newHashSet(publisher1, publisher2))

		when:
		BookSeries bookSeries = bookSeriesTemplateProcessor.process(bookSeriesTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, BookSeries) >> UID
		0 * _
		bookSeries.uid == UID
		bookSeries.page == page
		bookSeries.title == TITLE
		bookSeries.publishedYearFrom == PUBLISHED_YEAR_FROM
		bookSeries.publishedMonthFrom == PUBLISHED_MONTH_FROM
		bookSeries.publishedYearTo == PUBLISHED_YEAR_TO
		bookSeries.publishedMonthTo == PUBLISHED_MONTH_TO
		bookSeries.numberOfBooks == NUMBER_OF_BOOKS
		bookSeries.yearFrom == YEAR_FROM
		bookSeries.yearTo == YEAR_TO
		bookSeries.miniseries == MINISERIES
		bookSeries.EBookSeries == E_BOOK_SERIES
		bookSeries.publishers.size() == 2
		bookSeries.publishers.contains publisher1
		bookSeries.publishers.contains publisher2
	}

}
