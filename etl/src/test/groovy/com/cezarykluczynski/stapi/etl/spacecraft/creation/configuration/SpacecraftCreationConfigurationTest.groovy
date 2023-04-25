package com.cezarykluczynski.stapi.etl.spacecraft.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.spacecraft.creation.processor.SpacecraftReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource

class SpacecraftCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_SPACECRAFTS = 'TITLE_SPACECRAFTS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SpacecraftCreationConfiguration spacecraftCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		spacecraftCreationConfiguration = new SpacecraftCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "SpacecraftReader is created with all pages when step is not completed"() {
		when:
		SpacecraftReader planetReader = spacecraftCreationConfiguration.spacecraftReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFTS) >> false
		1 * categoryApiMock.getPages(CategoryTitles.SPACECRAFTS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_SPACECRAFTS)
		0 * _
		categoryHeaderTitleList.contains TITLE_SPACECRAFTS
	}

	void "SpacecraftReader is created with no pages when step is completed"() {
		when:
		SpacecraftReader planetReader = spacecraftCreationConfiguration.spacecraftReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFTS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
