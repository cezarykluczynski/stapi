package com.cezarykluczynski.stapi.etl.technology.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.technology.creation.processor.TechnologyReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class TechnologyCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_TECHNOLOGY = 'TITLE_TECHNOLOGY'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private TechnologyCreationConfiguration technologyCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		technologyCreationConfiguration = new TechnologyCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	@SuppressWarnings('LineLength')
	void "TechnologyReader is created is created with all pages when step is not completed"() {
		when:
		TechnologyReader technologyReader = technologyCreationConfiguration.technologyReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(technologyReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TECHNOLOGY) >> false
		1 * categoryApiMock.getPages(CategoryTitles.TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TECHNOLOGY)
		0 * _
		categoryHeaderTitleList.contains TITLE_TECHNOLOGY
	}

	void "TechnologyReader is created with no pages when step is completed"() {
		when:
		TechnologyReader technologyReader = technologyCreationConfiguration.technologyReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(technologyReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TECHNOLOGY) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
