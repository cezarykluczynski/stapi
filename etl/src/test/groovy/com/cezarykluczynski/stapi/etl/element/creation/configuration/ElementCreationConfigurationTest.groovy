package com.cezarykluczynski.stapi.etl.element.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.element.creation.processor.ElementReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class ElementCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_ELEMENTS = 'TITLE_ELEMENTS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private ElementCreationConfiguration elementCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		elementCreationConfiguration = new ElementCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "ElementReader is created is created with all pages when step is not completed"() {
		when:
		ElementReader elementReader = elementCreationConfiguration.elementReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(elementReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ELEMENTS) >> false

		1 * categoryApiMock.getPages(CategoryTitle.ELEMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ELEMENTS)
		0 * _
		categoryHeaderTitleList.contains TITLE_ELEMENTS
	}

	void "ElementReader is created with no pages when step is completed"() {
		when:
		ElementReader elementReader = elementCreationConfiguration.elementReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(elementReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ELEMENTS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
