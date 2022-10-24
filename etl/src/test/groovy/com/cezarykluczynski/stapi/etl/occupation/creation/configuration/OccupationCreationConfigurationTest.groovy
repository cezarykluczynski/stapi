package com.cezarykluczynski.stapi.etl.occupation.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.occupation.creation.processor.OccupationReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class OccupationCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_OCCUPATIONS = 'TITLE_OCCUPATIONS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private OccupationCreationConfiguration occupationCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		occupationCreationConfiguration = new OccupationCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	@SuppressWarnings('LineLength')
	void "OccupationReader is created is created with all pages when step is not completed"() {
		when:
		OccupationReader occupationReader = occupationCreationConfiguration.occupationReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(occupationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_OCCUPATIONS) >> false

		1 * categoryApiMock.getPages(CategoryTitles.OCCUPATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_OCCUPATIONS)
		0 * _
		categoryHeaderTitleList.contains TITLE_OCCUPATIONS
	}

	void "OccupationReader is created with no pages when step is completed"() {
		when:
		OccupationReader occupationReader = occupationCreationConfiguration.occupationReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(occupationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_OCCUPATIONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
