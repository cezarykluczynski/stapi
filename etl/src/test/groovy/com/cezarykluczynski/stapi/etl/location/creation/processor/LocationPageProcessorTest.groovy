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
		1 * locationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.notFound()
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
		1 * locationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.notFound()
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
		1 * locationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.notFound()
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Location) >> UID
		location.uid == UID
	}

	@Unroll('when #categories is passed, expect #flagName set to true and #trueBooleans true booleans')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaders -> Lists.newArrayList(categoryHeaders[0].title)
		}
		1 * locationNameFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.notFound()

		expect:
		Location location = locationPageProcessor.process(new SourcesPage(categories: categories))
		location[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(location) == trueBooleans

		where:
		categories                                                 | flagName               | flag  | trueBooleans
		Lists.newArrayList()                                       | 'establishment'        | false | 0
		Lists.newArrayList()                                       | 'landmark'             | false | 0
		createList(CategoryTitle.LOCATIONS)                        | 'establishment'        | false | 0
		createList(CategoryTitle.EARTH_LOCATIONS)                  | 'earthlyLocation'      | true  | 1
		createList(CategoryTitle.EARTH_ROADS)                      | 'earthlyLocation'      | true  | 3
		createList(CategoryTitle.EARTH_ROADS)                      | 'road'                 | true  | 3
		createList(CategoryTitle.EARTH_ROADS)                      | 'structure'            | true  | 3
		createList(CategoryTitle.EARTH_ESTABLISHMENTS)             | 'earthlyLocation'      | true  | 2
		createList(CategoryTitle.EARTH_ESTABLISHMENTS)             | 'establishment'        | true  | 2
		createList(CategoryTitle.MEDICAL_ESTABLISHMENTS)           | 'medicalEstablishment' | true  | 2
		createList(CategoryTitle.MEDICAL_ESTABLISHMENTS)           | 'establishment'        | true  | 2
		createList(CategoryTitle.WARDS)                            | 'medicalEstablishment' | true  | 2
		createList(CategoryTitle.WARDS)                            | 'establishment'        | true  | 2
		createList(CategoryTitle.RESTAURANTS)                      | 'restaurant'           | true  | 2
		createList(CategoryTitle.RESTAURANTS)                      | 'establishment'        | true  | 2
		createList(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED) | 'medicalEstablishment' | true  | 2
		createList(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED) | 'establishment'        | true  | 2
		createList(CategoryTitle.SCHOOLS)                          | 'school'               | true  | 2
		createList(CategoryTitle.SCHOOLS)                          | 'establishment'        | true  | 2
		createList(CategoryTitle.EARTH_SCHOOLS)                    | 'earthlyLocation'      | true  | 3
		createList(CategoryTitle.EARTH_SCHOOLS)                    | 'school'               | true  | 3
		createList(CategoryTitle.EARTH_SCHOOLS)                    | 'establishment'        | true  | 3
		createList(CategoryTitle.STARFLEET_SCHOOLS)                | 'school'               | true  | 2
		createList(CategoryTitle.STARFLEET_SCHOOLS)                | 'establishment'        | true  | 2
		createList(CategoryTitle.ESTABLISHMENTS)                   | 'establishment'        | true  | 1
		createList(CategoryTitle.ESTABLISHMENTS_RETCONNED)         | 'establishment'        | true  | 1
		createList(CategoryTitle.DS9_ESTABLISHMENTS)               | 'establishment'        | true  | 2
		createList(CategoryTitle.DS9_ESTABLISHMENTS)               | 'ds9Establishment'     | true  | 2
		createList(CategoryTitle.GEOGRAPHY)                        | 'geographicalLocation' | true  | 1
		createList(CategoryTitle.FICTIONAL_LOCATIONS)              | 'fictionalLocation'    | true  | 1
		createList(CategoryTitle.MYTHOLOGICAL_LOCATIONS)           | 'mythologicalLocation' | true  | 1
		createList(CategoryTitle.BODIES_OF_WATER)                  | 'bodyOfWater'          | true  | 2
		createList(CategoryTitle.BODIES_OF_WATER)                  | 'geographicalLocation' | true  | 2
		createList(CategoryTitle.EARTH_BODIES_OF_WATER)            | 'earthlyLocation'      | true  | 3
		createList(CategoryTitle.EARTH_BODIES_OF_WATER)            | 'bodyOfWater'          | true  | 3
		createList(CategoryTitle.EARTH_BODIES_OF_WATER)            | 'geographicalLocation' | true  | 3
		createList(CategoryTitle.COUNTRIES)                        | 'geographicalLocation' | true  | 2
		createList(CategoryTitle.COUNTRIES)                        | 'country'              | true  | 2
		createList(CategoryTitle.EARTH_COUNTRIES)                  | 'geographicalLocation' | true  | 3
		createList(CategoryTitle.EARTH_COUNTRIES)                  | 'earthlyLocation'      | true  | 3
		createList(CategoryTitle.EARTH_COUNTRIES)                  | 'country'              | true  | 3
		createList(CategoryTitle.SUBNATIONAL_ENTITIES)             | 'subnationalEntity'    | true  | 1
		createList(CategoryTitle.SUBNATIONAL_ENTITIES_RETCONNED)   | 'subnationalEntity'    | true  | 1
		createList(CategoryTitle.EARTH_SUBNATIONAL_ENTITIES)       | 'earthlyLocation'      | true  | 2
		createList(CategoryTitle.EARTH_SUBNATIONAL_ENTITIES)       | 'subnationalEntity'    | true  | 2
		createList(CategoryTitle.SETTLEMENTS)                      | 'settlement'           | true  | 3
		createList(CategoryTitle.SETTLEMENTS)                      | 'subnationalEntity'    | true  | 3
		createList(CategoryTitle.SETTLEMENTS)                      | 'geographicalLocation' | true  | 3
		createList(CategoryTitle.SETTLEMENTS_RETCONNED)            | 'settlement'           | true  | 3
		createList(CategoryTitle.SETTLEMENTS_RETCONNED)            | 'subnationalEntity'    | true  | 3
		createList(CategoryTitle.SETTLEMENTS_RETCONNED)            | 'geographicalLocation' | true  | 3
		createList(CategoryTitle.BAJORAN_SETTLEMENTS)              | 'subnationalEntity'    | true  | 4
		createList(CategoryTitle.BAJORAN_SETTLEMENTS)              | 'settlement'           | true  | 4
		createList(CategoryTitle.BAJORAN_SETTLEMENTS)              | 'bajoranSettlement'    | true  | 4
		createList(CategoryTitle.BAJORAN_SETTLEMENTS)              | 'geographicalLocation' | true  | 4
		createList(CategoryTitle.COLONIES)                         | 'subnationalEntity'    | true  | 4
		createList(CategoryTitle.COLONIES)                         | 'settlement'           | true  | 4
		createList(CategoryTitle.COLONIES)                         | 'colony'               | true  | 4
		createList(CategoryTitle.COLONIES)                         | 'geographicalLocation' | true  | 4
		createList(CategoryTitle.EARTH_SETTLEMENTS)                | 'subnationalEntity'    | true  | 4
		createList(CategoryTitle.EARTH_SETTLEMENTS)                | 'earthlyLocation'      | true  | 4
		createList(CategoryTitle.EARTH_SETTLEMENTS)                | 'settlement'           | true  | 4
		createList(CategoryTitle.EARTH_SETTLEMENTS)                | 'geographicalLocation' | true  | 4
		createList(CategoryTitle.US_SETTLEMENTS)                   | 'earthlyLocation'      | true  | 5
		createList(CategoryTitle.US_SETTLEMENTS)                   | 'subnationalEntity'    | true  | 5
		createList(CategoryTitle.US_SETTLEMENTS)                   | 'settlement'           | true  | 5
		createList(CategoryTitle.US_SETTLEMENTS)                   | 'usSettlement'         | true  | 5
		createList(CategoryTitle.US_SETTLEMENTS)                   | 'geographicalLocation' | true  | 5
		createList(CategoryTitle.US_SETTLEMENTS_RETCONNED)         | 'earthlyLocation'      | true  | 5
		createList(CategoryTitle.US_SETTLEMENTS_RETCONNED)         | 'subnationalEntity'    | true  | 5
		createList(CategoryTitle.US_SETTLEMENTS_RETCONNED)         | 'settlement'           | true  | 5
		createList(CategoryTitle.US_SETTLEMENTS_RETCONNED)         | 'usSettlement'         | true  | 5
		createList(CategoryTitle.US_SETTLEMENTS_RETCONNED)         | 'geographicalLocation' | true  | 5
		createList(CategoryTitle.QONOS_LOCATIONS)                  | 'qonosLocation'        | true  | 1
		createList(CategoryTitle.QONOS_SETTLEMENTS)                | 'qonosLocation'        | true  | 4
		createList(CategoryTitle.QONOS_SETTLEMENTS)                | 'settlement'           | true  | 4
		createList(CategoryTitle.QONOS_SETTLEMENTS)                | 'subnationalEntity'    | true  | 4
		createList(CategoryTitle.QONOS_SETTLEMENTS)                | 'geographicalLocation' | true  | 4
		createList(CategoryTitle.EARTH_GEOGRAPHY)                  | 'earthlyLocation'      | true  | 2
		createList(CategoryTitle.EARTH_GEOGRAPHY)                  | 'geographicalLocation' | true  | 2
		createList(CategoryTitle.LANDFORMS)                        | 'landform'             | true  | 1
		createList(CategoryTitle.RELIGIOUS_LOCATIONS)              | 'religiousLocation'    | true  | 1
		createList(CategoryTitle.STRUCTURES)                       | 'structure'            | true  | 1
		createList(CategoryTitle.EARTH_STRUCTURES)                 | 'structure'            | true  | 2
		createList(CategoryTitle.EARTH_STRUCTURES)                 | 'earthlyLocation'      | true  | 2
		createList(CategoryTitle.BUILDING_INTERIORS)               | 'structure'            | true  | 2
		createList(CategoryTitle.BUILDING_INTERIORS)               | 'buildingInterior'     | true  | 2
		createList(CategoryTitle.ROADS)                            | 'road'                 | true  | 2
		createList(CategoryTitle.ROADS)                            | 'structure'            | true  | 2
		createList(CategoryTitle.SHIPYARDS)                        | 'shipyard'             | true  | 2
		createList(CategoryTitle.SHIPYARDS)                        | 'structure'            | true  | 2
		createList(CategoryTitle.RESIDENCES)                       | 'residence'            | true  | 2
		createList(CategoryTitle.RESIDENCES)                       | 'structure'            | true  | 2
		createList(CategoryTitle.MIRROR_UNIVERSE)                  | 'mirror'               | true  | 1
		createList(CategoryTitle.ALTERNATE_REALITY)                | 'alternateReality'     | true  | 1
		createList(CategoryTitle.LOCATIONS_ALTERNATE_REALITY)      | 'alternateReality'     | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
