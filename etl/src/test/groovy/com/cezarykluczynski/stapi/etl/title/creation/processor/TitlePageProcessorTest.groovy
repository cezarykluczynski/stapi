package com.cezarykluczynski.stapi.etl.title.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.title.creation.service.TitlePageFilter
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class TitlePageProcessorTest extends Specification {

	private static final String NAME_WITH_BRACKETS = 'NAME (with brackets)'
	private static final String NAME = 'NAME'
	private static final String UID = 'UID'

	private TitlePageFilter titlePageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private TitlePageProcessor titlePageProcessor

	void setup() {
		titlePageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		titlePageProcessor = new TitlePageProcessor(titlePageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Title title = titlePageProcessor.process(page)

		then:
		1 * titlePageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		title == null
	}

	void "name is cleaned"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME_WITH_BRACKETS)

		when:
		Title title = titlePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		title.name == NAME
	}

	void "page is bound"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Title title = titlePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		title.name == NAME
		title.page == modelPage
	}

	void "UID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Title title = titlePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Title) >> UID
		title.uid == UID
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		Title title = titlePageProcessor.process(page)
		title[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(title) == trueBooleans

		where:
		page                                                                    | flagName         | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                       | 'militaryRank'   | false | 0
		new SourcesPage(categories: createList(CategoryTitle.MILITARY_RANKS))   | 'militaryRank'   | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.RELIGIOUS_TITLES)) | 'religiousTitle' | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
