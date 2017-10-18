package com.cezarykluczynski.stapi.etl.medical_condition.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.medical_condition.creation.processor.MedicalConditionReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class MedicalConditionCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_MEDICAL_CONDITIONS = 'TITLE_MEDICAL_CONDITIONS'
	private static final String TITLE_PSYCHOLOGICAL_CONDITIONS = 'TITLE_PSYCHOLOGICAL_CONDITIONS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private MedicalConditionCreationConfiguration medicalConditionCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		medicalConditionCreationConfiguration = new MedicalConditionCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	@SuppressWarnings('LineLength')
	void "MedicalConditionReader is created is created with all pages when step is not completed"() {
		when:
		MedicalConditionReader medicalConditionReader = medicalConditionCreationConfiguration.medicalConditionReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(medicalConditionReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MEDICAL_CONDITIONS) >> false

		1 * categoryApiMock.getPages(CategoryTitle.MEDICAL_CONDITIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MEDICAL_CONDITIONS)
		1 * categoryApiMock.getPages(CategoryTitle.PSYCHOLOGICAL_CONDITIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_PSYCHOLOGICAL_CONDITIONS)
		0 * _
		categoryHeaderTitleList.contains TITLE_MEDICAL_CONDITIONS
		categoryHeaderTitleList.contains TITLE_PSYCHOLOGICAL_CONDITIONS
	}

	void "MedicalConditionReader is created with no pages when step is completed"() {
		when:
		MedicalConditionReader medicalConditionReader = medicalConditionCreationConfiguration.medicalConditionReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(medicalConditionReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MEDICAL_CONDITIONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
