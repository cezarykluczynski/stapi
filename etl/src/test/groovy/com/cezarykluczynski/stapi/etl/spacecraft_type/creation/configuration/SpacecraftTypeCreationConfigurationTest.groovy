package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor.SpacecraftTypeReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource

class SpacecraftTypeCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String SPACECRAFT_TYPES_TITLE = 'SPACECRAFT_TYPES_TITLE'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SpacecraftTypeCreationConfiguration spacecraftTypeCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		spacecraftTypeCreationConfiguration = new SpacecraftTypeCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "SpacecraftTypeReader is created with all pages when step is not completed"() {
		when:
		SpacecraftTypeReader planetReader = spacecraftTypeCreationConfiguration.spacecraftTypeReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFT_TYPES) >> false
		1 * categoryApiMock.getPages(CategoryTitles.SPACECRAFT_TYPES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(SPACECRAFT_TYPES_TITLE)
		0 * _
		categoryHeaderTitleList.contains SPACECRAFT_TYPES_TITLE
	}

	void "SpacecraftTypeReader is created with no pages when step is completed"() {
		when:
		SpacecraftTypeReader planetReader = spacecraftTypeCreationConfiguration.spacecraftTypeReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFT_TYPES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
