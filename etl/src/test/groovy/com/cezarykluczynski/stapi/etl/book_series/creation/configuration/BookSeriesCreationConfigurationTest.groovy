package com.cezarykluczynski.stapi.etl.book_series.creation.configuration

import com.cezarykluczynski.stapi.etl.book_series.creation.processor.BookSeriesReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class BookSeriesCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_BOOK_SERIES = 'TITLE_BOOK_SERIES'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private BookSeriesCreationConfiguration bookSeriesCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		bookSeriesCreationConfiguration = new BookSeriesCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "BookSeriesReader is created with all pages when step is not completed"() {
		when:
		BookSeriesReader bookSeriesReader = bookSeriesCreationConfiguration.bookSeriesReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(bookSeriesReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_BOOK_SERIES) >> false
		1 * categoryApiMock.getPages(CategoryTitles.BOOK_SERIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_BOOK_SERIES)
		0 * _
		categoryHeaderTitleList.contains TITLE_BOOK_SERIES
	}

	void "BookSeriesReader is created with no pages when step is completed"() {
		when:
		BookSeriesReader bookSeriesReader = bookSeriesCreationConfiguration.bookSeriesReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(bookSeriesReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_BOOK_SERIES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
