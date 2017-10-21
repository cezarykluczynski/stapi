package com.cezarykluczynski.stapi.etl.occupation.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.occupation.creation.service.OccupationPageFilter
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class OccupationPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'
	private static final String NAME = 'NAME'
	private static final String UID = 'UID'

	private OccupationPageFilter occupationPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private OccupationPageProcessor occupationPageProcessor

	void setup() {
		occupationPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		occupationPageProcessor = new OccupationPageProcessor(occupationPageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Occupation occupation = occupationPageProcessor.process(page)

		then:
		1 * occupationPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		occupation == null
	}

	void "sets name from page title, and cuts brackets when they are present"() {
		given:
		SourcesPage page = new SourcesPage(title: TITLE_WITH_BRACKETS)

		when:
		Occupation occupation = occupationPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		occupation.name == TITLE
	}

	void "page is bound"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Occupation occupation = occupationPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		occupation.page == modelPage
	}

	void "UID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Occupation occupation = occupationPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Occupation) >> UID
		occupation.uid == UID
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		Occupation occupation = occupationPageProcessor.process(page)
		occupation[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(occupation) == trueBooleans

		where:
		page                                                                          | flagName               | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                             | 'legalOccupation'      | false | 0
		new SourcesPage(categories: createList(CategoryTitle.LEGAL_OCCUPATIONS))      | 'legalOccupation'      | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.MEDICAL_OCCUPATIONS))    | 'medicalOccupation'    | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SCIENTIFIC_OCCUPATIONS)) | 'scientificOccupation' | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
