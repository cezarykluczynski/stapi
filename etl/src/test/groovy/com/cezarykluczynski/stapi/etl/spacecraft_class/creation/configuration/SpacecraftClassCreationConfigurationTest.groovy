package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor.SpacecraftClassReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists

class SpacecraftClassCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_SPACECRAFT_CLASSES = 'TITLE_SPACECRAFT_CLASSES'
	private static final String TITLE_STARSHIP_CLASSES = 'TITLE_STARSHIP_CLASSES'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SpacecraftClassCreationConfiguration spacecraftClassCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		spacecraftClassCreationConfiguration = new SpacecraftClassCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "SpacecraftClassReader is created with all pages when step is not completed"() {
		when:
		SpacecraftClassReader planetReader = spacecraftClassCreationConfiguration.spacecraftClassReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFT_CLASSES) >> false
		1 * categoryApiMock.getPages(CategoryTitles.SPACECRAFT_CLASSES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_SPACECRAFT_CLASSES)
		1 * categoryApiMock.getPagesIncludingSubcategoriesExcept(CategoryTitle.STARSHIP_CLASSES, Lists.newArrayList(
				CategoryTitle.MEMORY_ALPHA_NON_CANON_REDIRECTS_STARSHIP_CLASSES), MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_STARSHIP_CLASSES)
		0 * _
		categoryHeaderTitleList.contains TITLE_SPACECRAFT_CLASSES
		categoryHeaderTitleList.contains TITLE_STARSHIP_CLASSES
	}

	void "SpacecraftClassReader is created with no pages when step is completed"() {
		when:
		SpacecraftClassReader planetReader = spacecraftClassCreationConfiguration.spacecraftClassReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFT_CLASSES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
