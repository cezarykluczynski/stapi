package com.cezarykluczynski.stapi.etl.book_collection.creation.configuration

import com.cezarykluczynski.stapi.etl.book_collection.creation.processor.BookCollectionReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class BookCollectionCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_NOVEL_COLLECTION = 'TITLE_NOVEL_COLLECTION'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private BookCollectionCreationConfiguration bookCollectionCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		bookCollectionCreationConfiguration = new BookCollectionCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "BookCollectionReader is created with all pages when step is not completed"() {
		when:
		BookCollectionReader bookCollectionReader = bookCollectionCreationConfiguration.bookCollectionReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(bookCollectionReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_BOOK_COLLECTIONS) >> false
		1 * categoryApiMock.getPages(CategoryTitle.NOVEL_COLLECTIONS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_NOVEL_COLLECTION)
		0 * _
		categoryHeaderTitleList.contains TITLE_NOVEL_COLLECTION
	}

	void "BookCollectionReader is created with no pages when step is completed"() {
		when:
		BookCollectionReader bookCollectionReader = bookCollectionCreationConfiguration.bookCollectionReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(bookCollectionReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_BOOK_COLLECTIONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
