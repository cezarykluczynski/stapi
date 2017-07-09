package com.cezarykluczynski.stapi.etl.book.creation.configuration

import com.cezarykluczynski.stapi.etl.book.creation.processor.BookReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class BookCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_BOOKS = 'TITLE_BOOKS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private BookCreationConfiguration bookCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		bookCreationConfiguration = new BookCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "BookReader is created with all pages when step is not completed"() {
		when:
		BookReader bookReader = bookCreationConfiguration.bookReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(bookReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_BOOKS) >> false
		1 * categoryApiMock.getPages(CategoryTitles.BOOKS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_BOOKS)
		0 * _
		categoryHeaderTitleList.contains TITLE_BOOKS
	}

	void "BookReader is created with no pages when step is completed"() {
		when:
		BookReader bookReader = bookCreationConfiguration.bookReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(bookReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_BOOKS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
