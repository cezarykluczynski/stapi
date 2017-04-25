package com.cezarykluczynski.stapi.etl.location.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.location.creation.service.LocationPageFilter
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class LocationPageProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String NAME_CORRECTED = 'NAME_CORRECTED'
	private static final String UID = 'UID'

	private LocationPageFilter locationPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private LocationNameFixedValueProvider locationNameFixedValueProviderMock

	private LocationPageProcessor locationPageProcessor

	void setup() {
		locationPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		locationNameFixedValueProviderMock = Mock()
		locationPageProcessor = new LocationPageProcessor(locationPageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock, locationNameFixedValueProviderMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Location location = locationPageProcessor.process(page)

		then:
		1 * locationPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		location == null
	}

	void "sets title from LocationNameFixedValueProvider when it is found"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)

		when:
		Location location = locationPageProcessor.process(page)

		then:
		1 * locationPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * locationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.found(NAME_CORRECTED)
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		location.name == NAME_CORRECTED
	}

	void "keeps original title when LocationNameFixedValueProvider found nothing"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)

		when:
		Location location = locationPageProcessor.process(page)

		then:
		1 * locationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		location.name == NAME
	}

	void "page is bound"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Location location = locationPageProcessor.process(page)

		then:
		1 * locationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		location.page == modelPage
	}

	void "UID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Location location = locationPageProcessor.process(page)

		then:
		1 * locationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Location) >> UID
		location.uid == UID
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		1 * locationNameFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()

		expect:
		Location location = locationPageProcessor.process(page)
		location[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(location) == trueBooleans

		where:
		page                                                                                    | flagName               | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                                       | 'establishment'        | false | 0
		new SourcesPage(categories: createList(CategoryTitle.EARTH_LOCATIONS))                  | 'earthlyLocation'      | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.EARTH_LANDMARKS))                  | 'earthlyLocation'      | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_LANDMARKS))                  | 'structure'            | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_LANDMARKS))                  | 'landmark'             | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_ROADS))                      | 'earthlyLocation'      | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_ROADS))                      | 'road'                 | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_ESTABLISHMENTS))             | 'earthlyLocation'      | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_ESTABLISHMENTS))             | 'establishment'        | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.MEDICAL_ESTABLISHMENTS))           | 'medicalEstablishment' | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.MEDICAL_ESTABLISHMENTS))           | 'establishment'        | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.WARDS))                            | 'medicalEstablishment' | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.WARDS))                            | 'establishment'        | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED)) | 'medicalEstablishment' | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED)) | 'establishment'        | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.SCHOOLS))                          | 'school'               | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.SCHOOLS))                          | 'establishment'        | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_SCHOOLS))                    | 'earthlyLocation'      | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_SCHOOLS))                    | 'school'               | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_SCHOOLS))                    | 'establishment'        | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.STARFLEET_SCHOOLS))                | 'school'               | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.STARFLEET_SCHOOLS))                | 'establishment'        | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.ESTABLISHMENTS))                   | 'establishment'        | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.ESTABLISHMENTS_RETCONNED))         | 'establishment'        | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.DS9_ESTABLISHMENTS))               | 'establishment'        | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.DS9_ESTABLISHMENTS))               | 'ds9Establishment'     | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.GEOGRAPHY))                        | 'geographicalLocation' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.FICTIONAL_LOCATIONS))              | 'fictionalLocation'    | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.BODIES_OF_WATER))                  | 'bodyOfWater'          | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.EARTH_BODIES_OF_WATER))            | 'earthlyLocation'      | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_BODIES_OF_WATER))            | 'bodyOfWater'          | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.COUNTRIES))                        | 'geographicalLocation' | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.COUNTRIES))                        | 'country'              | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_COUNTRIES))                  | 'geographicalLocation' | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_COUNTRIES))                  | 'earthlyLocation'      | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_COUNTRIES))                  | 'country'              | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.SUBNATIONAL_ENTITIES))             | 'subnationalEntity'    | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SUBNATIONAL_ENTITIES_RETCONNED))   | 'subnationalEntity'    | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.EARTH_SUBNATIONAL_ENTITIES))       | 'earthlyLocation'      | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_SUBNATIONAL_ENTITIES))       | 'subnationalEntity'    | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.SETTLEMENTS))                      | 'subnationalEntity'    | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.SETTLEMENTS))                      | 'settlement'           | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.SETTLEMENTS_RETCONNED))            | 'settlement'           | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.SETTLEMENTS_RETCONNED))            | 'settlement'           | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.BAJORAN_SETTLEMENTS))              | 'subnationalEntity'    | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.BAJORAN_SETTLEMENTS))              | 'settlement'           | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.BAJORAN_SETTLEMENTS))              | 'bajoranSettlement'    | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.COLONIES))                         | 'subnationalEntity'    | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.COLONIES))                         | 'settlement'           | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.COLONIES))                         | 'colony'               | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_SETTLEMENTS))                | 'subnationalEntity'    | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_SETTLEMENTS))                | 'earthlyLocation'      | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.EARTH_SETTLEMENTS))                | 'settlement'           | true  | 3
		new SourcesPage(categories: createList(CategoryTitle.US_SETTLEMENTS))                   | 'earthlyLocation'      | true  | 4
		new SourcesPage(categories: createList(CategoryTitle.US_SETTLEMENTS))                   | 'subnationalEntity'    | true  | 4
		new SourcesPage(categories: createList(CategoryTitle.US_SETTLEMENTS))                   | 'settlement'           | true  | 4
		new SourcesPage(categories: createList(CategoryTitle.US_SETTLEMENTS))                   | 'usSettlement'         | true  | 4
		new SourcesPage(categories: createList(CategoryTitle.US_SETTLEMENTS_RETCONNED))         | 'earthlyLocation'      | true  | 4
		new SourcesPage(categories: createList(CategoryTitle.US_SETTLEMENTS_RETCONNED))         | 'subnationalEntity'    | true  | 4
		new SourcesPage(categories: createList(CategoryTitle.US_SETTLEMENTS_RETCONNED))         | 'settlement'           | true  | 4
		new SourcesPage(categories: createList(CategoryTitle.US_SETTLEMENTS_RETCONNED))         | 'usSettlement'         | true  | 4
		new SourcesPage(categories: createList(CategoryTitle.EARTH_GEOGRAPHY))                  | 'earthlyLocation'      | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_GEOGRAPHY))                  | 'geographicalLocation' | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.LANDFORMS))                        | 'landform'             | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.RELIGIOUS_LOCATIONS))              | 'religiousLocation'    | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.STRUCTURES))                       | 'structure'            | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.BUILDING_INTERIORS))               | 'structure'            | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.BUILDING_INTERIORS))               | 'buildingInterior'     | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.LANDMARKS))                        | 'structure'            | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.LANDMARKS))                        | 'landmark'             | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.ROADS))                            | 'road'                 | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SHIPYARDS))                        | 'shipyard'             | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.MIRROR_UNIVERSE))                  | 'mirror'               | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.ALTERNATE_REALITY))                | 'alternateReality'     | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.LOCATIONS_ALTERNATE_REALITY))      | 'alternateReality'     | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
