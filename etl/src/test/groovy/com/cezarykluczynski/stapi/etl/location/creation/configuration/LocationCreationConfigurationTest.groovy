package com.cezarykluczynski.stapi.etl.location.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import org.springframework.context.ApplicationContext

class LocationCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_LOCATIONS = 'TITLE_LOCATIONS'

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private LocationCreationConfiguration locationCreationConfiguration

	void setup() {
		applicationContextMock = Mock()
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		locationCreationConfiguration = new LocationCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "LocationReader is created is created with all pages when step is not completed"() {
		when:
		LocationReader locationReader = locationCreationConfiguration.locationReader()
		List<String> categoryHeaderTitleList = readerToList(locationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_LOCATIONS) >> false
		1 * categoryApiMock.getPages(CategoryTitles.LOCATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_LOCATIONS)
		0 * _
		categoryHeaderTitleList.contains TITLE_LOCATIONS
	}

	void "LocationReader is created with no pages when step is completed"() {
		when:
		LocationReader locationReader = locationCreationConfiguration.locationReader()
		List<String> categoryHeaderTitleList = readerToList(locationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_LOCATIONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
