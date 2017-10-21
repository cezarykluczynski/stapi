package com.cezarykluczynski.stapi.etl.occupation.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.occupation.creation.processor.OccupationReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class OccupationCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_OCCUPATIONS = 'TITLE_OCCUPATIONS'
	private static final String TITLE_LEGAL_OCCUPATIONS = 'TITLE_LEGAL_OCCUPATIONS'
	private static final String TITLE_MEDICAL_OCCUPATIONS = 'TITLE_MEDICAL_OCCUPATIONS'
	private static final String TITLE_SCIENTIFIC_OCCUPATIONS = 'TITLE_SCIENTIFIC_OCCUPATIONS'

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

		1 * categoryApiMock.getPages(CategoryTitle.OCCUPATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_OCCUPATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.LEGAL_OCCUPATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_LEGAL_OCCUPATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.MEDICAL_OCCUPATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MEDICAL_OCCUPATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.SCIENTIFIC_OCCUPATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SCIENTIFIC_OCCUPATIONS)
		0 * _
		categoryHeaderTitleList.contains TITLE_OCCUPATIONS
		categoryHeaderTitleList.contains TITLE_LEGAL_OCCUPATIONS
		categoryHeaderTitleList.contains TITLE_MEDICAL_OCCUPATIONS
		categoryHeaderTitleList.contains TITLE_SCIENTIFIC_OCCUPATIONS
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
