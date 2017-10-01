package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor.SpacecraftTypeReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class SpacecraftTypeCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String SPACECRAFT_CLASSIFICATIONS_TITLE = 'SPACECRAFT_CLASSIFICATIONS_TITLE'
	private static final String STATION_TYPES_TITLE = 'STATION_TYPES_TITLE'

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
		1 * categoryApiMock.getPages(CategoryTitle.SPACECRAFT_CLASSIFICATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(SPACECRAFT_CLASSIFICATIONS_TITLE)
		1 * categoryApiMock.getPages(CategoryTitle.STATION_TYPES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(STATION_TYPES_TITLE)
		0 * _
		categoryHeaderTitleList.contains SPACECRAFT_CLASSIFICATIONS_TITLE
		categoryHeaderTitleList.contains STATION_TYPES_TITLE
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
