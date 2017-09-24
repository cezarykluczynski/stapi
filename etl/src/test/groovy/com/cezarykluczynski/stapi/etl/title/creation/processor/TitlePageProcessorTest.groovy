package com.cezarykluczynski.stapi.etl.title.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.title.creation.service.RanksTemplateService
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

	private RanksTemplateService ranksTemplateServiceMock

	private TitlePageProcessor titlePageProcessor

	void setup() {
		titlePageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		ranksTemplateServiceMock = Mock()
		titlePageProcessor = new TitlePageProcessor(titlePageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock, ranksTemplateServiceMock)
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

	void "title is generated without categories and flags from RanksTemplateService"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		SourcesPage page = new SourcesPage(
				title: NAME_WITH_BRACKETS,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		Title title = titlePageProcessor.process(page)

		then:
		1 * titlePageFilterMock.shouldBeFilteredOut(page) >> false
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Title) >> UID
		1 * ranksTemplateServiceMock.isMilitaryRank(NAME_WITH_BRACKETS) >> false
		1 * ranksTemplateServiceMock.isFleetRank(NAME_WITH_BRACKETS) >> false
		1 * ranksTemplateServiceMock.isPosition(NAME_WITH_BRACKETS) >> false
		0 * _
		title.name == NAME
		title.uid == UID
		title.page == modelPage
		!title.militaryRank
		!title.fleetRank
		!title.religiousTitle
		!title.position
		!title.mirror
	}

	void "title is generated without categories and with flags from RanksTemplateService"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		SourcesPage page = new SourcesPage(
				title: NAME_WITH_BRACKETS,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		Title title = titlePageProcessor.process(page)

		then:
		1 * titlePageFilterMock.shouldBeFilteredOut(page) >> false
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Title) >> UID
		1 * ranksTemplateServiceMock.isMilitaryRank(NAME_WITH_BRACKETS) >> true
		1 * ranksTemplateServiceMock.isFleetRank(NAME_WITH_BRACKETS) >> true
		1 * ranksTemplateServiceMock.isPosition(NAME_WITH_BRACKETS) >> true
		0 * _
		title.name == NAME
		title.uid == UID
		title.page == modelPage
		title.militaryRank
		title.fleetRank
		!title.religiousTitle
		title.position
		!title.mirror
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
		new SourcesPage(categories: createList(CategoryTitle.MIRROR_UNIVERSE))  | 'mirror'         | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
