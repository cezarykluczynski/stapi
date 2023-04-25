package com.cezarykluczynski.stapi.etl.material.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.material.creation.processor.MaterialReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource

class MaterialCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_MATERIALS = 'TITLE_MATERIALS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private MaterialCreationConfiguration materialCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		materialCreationConfiguration = new MaterialCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "MaterialReader is created is created with all pages when step is not completed"() {
		when:
		MaterialReader materialReader = materialCreationConfiguration.materialReader()
		List<String> categoryHeaderMaterialList = pageHeaderReaderToList(materialReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MATERIALS) >> false
		1 * categoryApiMock.getPages(CategoryTitles.MATERIALS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MATERIALS)
		0 * _
		categoryHeaderMaterialList.contains TITLE_MATERIALS
	}

	void "MaterialReader is created with no pages when step is completed"() {
		when:
		MaterialReader materialReader = materialCreationConfiguration.materialReader()
		List<String> categoryHeaderMaterialList = pageHeaderReaderToList(materialReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MATERIALS) >> true
		0 * _
		categoryHeaderMaterialList.empty
	}

}
