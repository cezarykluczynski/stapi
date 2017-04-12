package com.cezarykluczynski.stapi.etl.location.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import org.springframework.context.ApplicationContext

class LocationCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_EARTH_ESTABLISHMENTS = 'TITLE_EARTH_ESTABLISHMENTS'
	private static final String TITLE_MEDICAL_ESTABLISHMENTS = 'TITLE_MEDICAL_ESTABLISHMENTS'
	private static final String TITLE_MEDICAL_ESTABLISHMENTS_RETCONNED = 'TITLE_MEDICAL_ESTABLISHMENTS_RETCONNED'
	private static final String TITLE_WARDS = 'TITLE_WARDS'
	private static final String TITLE_ESTABLISHMENTS = 'TITLE_ESTABLISHMENTS'
	private static final String TITLE_SCHOOLS = 'TITLE_SCHOOLS'
	private static final String TITLE_STARFLEET_SCHOOLS = 'TITLE_STARFLEET_SCHOOLS'
	private static final String TITLE_EARTH_SCHOOLS = 'TITLE_EARTH_SCHOOLS'
	private static final String TITLE_ESTABLISHMENTS_RETCONNED = 'TITLE_ESTABLISHMENTS_RETCONNED'
	private static final String TITLE_DS9_ESTABLISHMENTS = 'TITLE_DS9_ESTABLISHMENTS'

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

	@SuppressWarnings('LineLength')
	void "LocationReader is created is created with all pages when step is not completed"() {
		when:
		LocationReader locationReader = locationCreationConfiguration.locationReader()
		List<String> categoryHeaderTitleList = readerToList(locationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS) >> false

		1 * categoryApiMock.getPages(CategoryTitle.EARTH_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_ESTABLISHMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.MEDICAL_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MEDICAL_ESTABLISHMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MEDICAL_ESTABLISHMENTS_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.WARDS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_WARDS)
		1 * categoryApiMock.getPages(CategoryTitle.ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ESTABLISHMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.SCHOOLS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SCHOOLS)
		1 * categoryApiMock.getPages(CategoryTitle.STARFLEET_SCHOOLS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_STARFLEET_SCHOOLS)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_SCHOOLS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_SCHOOLS)
		1 * categoryApiMock.getPages(CategoryTitle.ESTABLISHMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ESTABLISHMENTS_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.DS9_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_DS9_ESTABLISHMENTS)
		0 * _
		categoryHeaderTitleList.contains TITLE_EARTH_ESTABLISHMENTS
		categoryHeaderTitleList.contains TITLE_MEDICAL_ESTABLISHMENTS
		categoryHeaderTitleList.contains TITLE_MEDICAL_ESTABLISHMENTS_RETCONNED
		categoryHeaderTitleList.contains TITLE_WARDS
		categoryHeaderTitleList.contains TITLE_ESTABLISHMENTS
		categoryHeaderTitleList.contains TITLE_SCHOOLS
		categoryHeaderTitleList.contains TITLE_STARFLEET_SCHOOLS
		categoryHeaderTitleList.contains TITLE_EARTH_SCHOOLS
		categoryHeaderTitleList.contains TITLE_ESTABLISHMENTS_RETCONNED
		categoryHeaderTitleList.contains TITLE_DS9_ESTABLISHMENTS
	}

	void "LocationReader is created with no pages when step is completed"() {
		when:
		LocationReader locationReader = locationCreationConfiguration.locationReader()
		List<String> categoryHeaderTitleList = readerToList(locationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
