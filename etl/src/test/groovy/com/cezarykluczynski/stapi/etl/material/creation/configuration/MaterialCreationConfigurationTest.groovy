package com.cezarykluczynski.stapi.etl.material.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.material.creation.processor.MaterialReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class MaterialCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_MATERIALS = 'TITLE_MATERIALS'
	private static final String TITLE_EXPLOSIVES = 'TITLE_EXPLOSIVES'
	private static final String TITLE_GEMSTONES = 'TITLE_GEMSTONES'
	private static final String TITLE_CHEMICAL_COMPOUNDS = 'TITLE_CHEMICAL_COMPOUNDS'

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
		1 * categoryApiMock.getPages(CategoryTitle.MATERIALS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MATERIALS)
		1 * categoryApiMock.getPages(CategoryTitle.EXPLOSIVES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EXPLOSIVES)
		1 * categoryApiMock.getPages(CategoryTitle.GEMSTONES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_GEMSTONES)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.CHEMICAL_COMPOUNDS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_CHEMICAL_COMPOUNDS)
		0 * _
		categoryHeaderMaterialList.contains TITLE_MATERIALS
		categoryHeaderMaterialList.contains TITLE_EXPLOSIVES
		categoryHeaderMaterialList.contains TITLE_GEMSTONES
		categoryHeaderMaterialList.contains TITLE_CHEMICAL_COMPOUNDS
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
