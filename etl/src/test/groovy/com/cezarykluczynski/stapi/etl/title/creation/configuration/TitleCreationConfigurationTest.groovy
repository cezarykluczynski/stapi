package com.cezarykluczynski.stapi.etl.title.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class TitleCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_TITLES = 'TITLE_TITLES'
	private static final String TITLE_MILITARY_RANKS = 'TITLE_MILITARY_RANKS'
	private static final String TITLE_RELIGIOUS_TITLES = 'TITLE_RELIGIOUS_TITLES'

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
		1 * categoryApiMock.getPages(CategoryTitle.TITLES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TITLES)
		1 * categoryApiMock.getPages(CategoryTitle.MILITARY_RANKS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_MILITARY_RANKS)
		1 * categoryApiMock.getPages(CategoryTitle.RELIGIOUS_TITLES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_RELIGIOUS_TITLES)
		0 * _
		categoryHeaderTitleList.contains TITLE_TITLES
		categoryHeaderTitleList.contains TITLE_MILITARY_RANKS
		categoryHeaderTitleList.contains TITLE_RELIGIOUS_TITLES
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
