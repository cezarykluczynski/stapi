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

	private static final String TITLE_LOCATIONS = 'TITLE_LOCATIONS'
	private static final String TITLE_EARTH_LOCATIONS = 'TITLE_EARTH_LOCATIONS'
	private static final String TITLE_EARTH_LANDMARKS = 'TITLE_EARTH_LANDMARKS'
	private static final String TITLE_EARTH_ROADS = 'TITLE_EARTH_ROADS'
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
	private static final String TITLE_FICTIONAL_LOCATIONS = 'TITLE_FICTIONAL_LOCATIONS'
	private static final String TITLE_GEOGRAPHY = 'TITLE_GEOGRAPHY'
	private static final String TITLE_BODIES_OF_WATER = 'TITLE_BODIES_OF_WATER'
	private static final String TITLE_EARTH_BODIES_OF_WATER = 'TITLE_EARTH_BODIES_OF_WATER'
	private static final String TITLE_COUNTRIES = 'TITLE_COUNTRIES'
	private static final String TITLE_EARTH_COUNTRIES = 'TITLE_EARTH_COUNTRIES'
	private static final String TITLE_SUBNATIONAL_ENTITIES = 'TITLE_SUBNATIONAL_ENTITIES'
	private static final String TITLE_SUBNATIONAL_ENTITIES_RETCONNED = 'TITLE_SUBNATIONAL_ENTITIES_RETCONNED'
	private static final String TITLE_EARTH_SUBNATIONAL_ENTITIES = 'TITLE_EARTH_SUBNATIONAL_ENTITIES'
	private static final String TITLE_SETTLEMENTS = 'TITLE_SETTLEMENTS'
	private static final String TITLE_BAJORAN_SETTLEMENTS = 'TITLE_BAJORAN_SETTLEMENTS'
	private static final String TITLE_COLONIES = 'TITLE_COLONIES'
	private static final String TITLE_SETTLEMENTS_RETCONNED = 'TITLE_SETTLEMENTS_RETCONNED'
	private static final String TITLE_EARTH_SETTLEMENTS = 'TITLE_EARTH_SETTLEMENTS'
	private static final String TITLE_US_SETTLEMENTS = 'TITLE_US_SETTLEMENTS'
	private static final String TITLE_US_SETTLEMENTS_RETCONNED = 'TITLE_US_SETTLEMENTS_RETCONNED'
	private static final String TITLE_EARTH_GEOGRAPHY = 'TITLE_EARTH_GEOGRAPHY'
	private static final String TITLE_LANDFORMS = 'TITLE_LANDFORMS'
	private static final String TITLE_RELIGIOUS_LOCATIONS = 'TITLE_RELIGIOUS_LOCATIONS'
	private static final String TITLE_STRUCTURES = 'TITLE_STRUCTURES'
	private static final String TITLE_BUILDING_INTERIORS = 'TITLE_BUILDING_INTERIORS'
	private static final String TITLE_LANDMARKS = 'TITLE_LANDMARKS'
	private static final String TITLE_ROADS = 'TITLE_ROADS'
	private static final String TITLE_SHIPYARDS = 'TITLE_SHIPYARDS'

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
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_LOCATIONS) >> false
		1 * categoryApiMock.getPages(CategoryTitle.LOCATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_LOCATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_LOCATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_LOCATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_LANDMARKS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_LANDMARKS)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_ROADS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_ROADS)
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
		1 * categoryApiMock.getPages(CategoryTitle.GEOGRAPHY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_GEOGRAPHY)
		1 * categoryApiMock.getPages(CategoryTitle.FICTIONAL_LOCATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_FICTIONAL_LOCATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.BODIES_OF_WATER, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_BODIES_OF_WATER)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_BODIES_OF_WATER, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_BODIES_OF_WATER)
		1 * categoryApiMock.getPages(CategoryTitle.COUNTRIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_COUNTRIES)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_COUNTRIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_COUNTRIES)
		1 * categoryApiMock.getPages(CategoryTitle.SUBNATIONAL_ENTITIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SUBNATIONAL_ENTITIES)
		1 * categoryApiMock.getPages(CategoryTitle.SUBNATIONAL_ENTITIES_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SUBNATIONAL_ENTITIES_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_SUBNATIONAL_ENTITIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_SUBNATIONAL_ENTITIES)
		1 * categoryApiMock.getPages(CategoryTitle.SETTLEMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SETTLEMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.BAJORAN_SETTLEMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_BAJORAN_SETTLEMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.COLONIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_COLONIES)
		1 * categoryApiMock.getPages(CategoryTitle.SETTLEMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SETTLEMENTS_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_SETTLEMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_SETTLEMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.US_SETTLEMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_US_SETTLEMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.US_SETTLEMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_US_SETTLEMENTS_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_GEOGRAPHY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_GEOGRAPHY)
		1 * categoryApiMock.getPages(CategoryTitle.LANDFORMS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_LANDFORMS)
		1 * categoryApiMock.getPages(CategoryTitle.RELIGIOUS_LOCATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_RELIGIOUS_LOCATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.STRUCTURES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_STRUCTURES)
		1 * categoryApiMock.getPages(CategoryTitle.BUILDING_INTERIORS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_BUILDING_INTERIORS)
		1 * categoryApiMock.getPages(CategoryTitle.LANDMARKS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_LANDMARKS)
		1 * categoryApiMock.getPages(CategoryTitle.ROADS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ROADS)
		1 * categoryApiMock.getPages(CategoryTitle.SHIPYARDS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SHIPYARDS)
		0 * _
		categoryHeaderTitleList.contains TITLE_LOCATIONS
		categoryHeaderTitleList.contains TITLE_EARTH_LOCATIONS
		categoryHeaderTitleList.contains TITLE_EARTH_LANDMARKS
		categoryHeaderTitleList.contains TITLE_EARTH_ROADS
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
		categoryHeaderTitleList.contains TITLE_GEOGRAPHY
		categoryHeaderTitleList.contains TITLE_FICTIONAL_LOCATIONS
		categoryHeaderTitleList.contains TITLE_BODIES_OF_WATER
		categoryHeaderTitleList.contains TITLE_EARTH_BODIES_OF_WATER
		categoryHeaderTitleList.contains TITLE_COUNTRIES
		categoryHeaderTitleList.contains TITLE_EARTH_COUNTRIES
		categoryHeaderTitleList.contains TITLE_SUBNATIONAL_ENTITIES
		categoryHeaderTitleList.contains TITLE_SUBNATIONAL_ENTITIES_RETCONNED
		categoryHeaderTitleList.contains TITLE_EARTH_SUBNATIONAL_ENTITIES
		categoryHeaderTitleList.contains TITLE_SETTLEMENTS
		categoryHeaderTitleList.contains TITLE_BAJORAN_SETTLEMENTS
		categoryHeaderTitleList.contains TITLE_COLONIES
		categoryHeaderTitleList.contains TITLE_SETTLEMENTS_RETCONNED
		categoryHeaderTitleList.contains TITLE_EARTH_SETTLEMENTS
		categoryHeaderTitleList.contains TITLE_US_SETTLEMENTS
		categoryHeaderTitleList.contains TITLE_US_SETTLEMENTS_RETCONNED
		categoryHeaderTitleList.contains TITLE_EARTH_GEOGRAPHY
		categoryHeaderTitleList.contains TITLE_LANDFORMS
		categoryHeaderTitleList.contains TITLE_RELIGIOUS_LOCATIONS
		categoryHeaderTitleList.contains TITLE_STRUCTURES
		categoryHeaderTitleList.contains TITLE_BUILDING_INTERIORS
		categoryHeaderTitleList.contains TITLE_LANDMARKS
		categoryHeaderTitleList.contains TITLE_ROADS
		categoryHeaderTitleList.contains TITLE_SHIPYARDS
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
