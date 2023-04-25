package com.cezarykluczynski.stapi.etl.title.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource

class TitleCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_TITLES = 'TITLE_TITLES'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private TitleCreationConfiguration titleCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		titleCreationConfiguration = new TitleCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "TitleReader is created is created with all pages when step is not completed"() {
		when:
		TitleReader titleReader = titleCreationConfiguration.titleReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(titleReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TITLES) >> false
		1 * categoryApiMock.getPages(CategoryTitles.TITLES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TITLES)
		0 * _
		categoryHeaderTitleList.contains TITLE_TITLES
	}

	void "TitleReader is created with no pages when step is completed"() {
		when:
		TitleReader titleReader = titleCreationConfiguration.titleReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(titleReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TITLES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
