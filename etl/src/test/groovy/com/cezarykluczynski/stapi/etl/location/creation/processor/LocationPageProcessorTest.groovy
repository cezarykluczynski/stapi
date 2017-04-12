package com.cezarykluczynski.stapi.etl.location.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.location.creation.service.LocationPageFilter
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
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
	private static final String GUID = 'GUID'

	private LocationPageFilter locationPageFilterMock

	private PageBindingService pageBindingServiceMock

	private GuidGenerator guidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private LocationNameFixedValueProvider locationNameFixedValueProviderMock

	private LocationPageProcessor locationPageProcessor

	void setup() {
		locationPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		guidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		locationNameFixedValueProviderMock = Mock()
		locationPageProcessor = new LocationPageProcessor(locationPageFilterMock, pageBindingServiceMock, guidGeneratorMock,
				categoryTitlesExtractingProcessorMock, locationNameFixedValueProviderMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock(SourcesPage)

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

	void "GUID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Location location = locationPageProcessor.process(page)

		then:
		1 * locationNameFixedValueProviderMock.getSearchedValue(NAME) >> FixedValueHolder.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * guidGeneratorMock.generateFromPage(modelPage, Location) >> GUID
		location.guid == GUID
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "set flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}
		locationNameFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()

		expect:
		Location location = locationPageProcessor.process(page)
		flag == location[flagName]
		trueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(location)

		where:
		page                                                                                    | flagName               | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                                       | 'establishment'        | false | 0
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
		new SourcesPage(categories: createList(CategoryTitle.ESTABLISHMENTS))                   | 'establishment'        | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.ESTABLISHMENTS_RETCONNED))         | 'establishment'        | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.DS9_ESTABLISHMENTS))               | 'establishment'        | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.DS9_ESTABLISHMENTS))               | 'ds9Establishment'     | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.MIRROR_UNIVERSE))                  | 'mirror'               | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.ALTERNATE_REALITY))                | 'alternateReality'     | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
