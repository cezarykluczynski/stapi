package com.cezarykluczynski.stapi.etl.astronomical_object.creation.configuration

import com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor.AstronomicalObjectReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class AstronomicalObjectCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_ASTRONOMICAL_OBJECTS = 'TITLE_ASTRONOMICAL_OBJECTS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private AstronomicalObjectCreationConfiguration astronomicalObjectCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		astronomicalObjectCreationConfiguration = new AstronomicalObjectCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "AstronomicalObjectReader is created with all pages when step is not completed"() {
		when:
		AstronomicalObjectReader planetReader = astronomicalObjectCreationConfiguration.astronomicalObjectReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ASTRONOMICAL_OBJECTS) >> false
		1 * categoryApiMock.getPages(CategoryTitles.ASTRONOMICAL_OBJECTS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_ASTRONOMICAL_OBJECTS)
		0 * _
		categoryHeaderTitleList.contains TITLE_ASTRONOMICAL_OBJECTS
	}

	void "AstronomicalObjectReader is created with no pages when step is completed"() {
		when:
		AstronomicalObjectReader planetReader = astronomicalObjectCreationConfiguration.astronomicalObjectReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ASTRONOMICAL_OBJECTS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
