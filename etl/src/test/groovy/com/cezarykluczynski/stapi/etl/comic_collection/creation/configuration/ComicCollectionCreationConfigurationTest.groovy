package com.cezarykluczynski.stapi.etl.comic_collection.creation.configuration

import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class ComicCollectionCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_COMIC_COLLECTION = 'TITLE_COMIC_COLLECTION'
	private static final String TITLE_PHOTONOVELS_COLLECTION = 'TITLE_PHOTONOVELS_COLLECTION'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private ComicCollectionCreationConfiguration comicCollectionCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		comicCollectionCreationConfiguration = new ComicCollectionCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "ComicCollectionReader is created with all pages when step is not completed"() {
		when:
		ComicCollectionReader comicCollectionReader = comicCollectionCreationConfiguration.comicCollectionReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(comicCollectionReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_COLLECTIONS) >> false
		1 * categoryApiMock.getPages(CategoryTitle.COMIC_COLLECTIONS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_COMIC_COLLECTION)
		1 * categoryApiMock.getPages(CategoryTitle.PHOTONOVELS_COLLECTIONS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_PHOTONOVELS_COLLECTION)
		0 * _
		categoryHeaderTitleList.contains TITLE_COMIC_COLLECTION
		categoryHeaderTitleList.contains TITLE_PHOTONOVELS_COLLECTION
	}

	void "ComicCollectionReader is created with no pages when step is completed"() {
		when:
		ComicCollectionReader comicCollectionReader = comicCollectionCreationConfiguration.comicCollectionReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(comicCollectionReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_COLLECTIONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
