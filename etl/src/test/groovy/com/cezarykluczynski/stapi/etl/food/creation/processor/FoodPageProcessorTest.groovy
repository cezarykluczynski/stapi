package com.cezarykluczynski.stapi.etl.food.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.food.creation.service.FoodPageFilter
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class FoodPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (beverage)'
	private static final String NAME = 'NAME'
	private static final String UID = 'UID'

	private FoodPageFilter foodPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private FoodPageProcessor foodPageProcessor

	void setup() {
		foodPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		foodPageProcessor = new FoodPageProcessor(foodPageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Food food = foodPageProcessor.process(page)

		then:
		1 * foodPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		food == null
	}

	void "sets name from page title, and cuts brackets when they are present"() {
		given:
		SourcesPage page = new SourcesPage(title: TITLE_WITH_BRACKETS)

		when:
		Food food = foodPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		food.name == TITLE
	}

	void "page is bound"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Food food = foodPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		food.page == modelPage
	}

	void "UID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Food food = foodPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Food) >> UID
		food.uid == UID
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		Food food = foodPageProcessor.process(page)
		food[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(food) == trueBooleans

		where:
		page                                                                       | flagName            | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                          | 'earthlyOrigin'     | false | 0
		new SourcesPage(categories: createList('Earth_beverages'))                 | 'earthlyOrigin'     | true  | 2
		new SourcesPage(categories: createList('Earth_beverages'))                 | 'beverage'          | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.DESSERTS))            | 'dessert'           | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.FRUITS))              | 'fruit'             | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.HERBS_AND_SPICES))    | 'herbOrSpice'       | true  | 1
		new SourcesPage(categories: createList('Earth_herbs_and_spices'))          | 'herbOrSpice'       | true  | 2
		new SourcesPage(categories: createList('Earth_herbs_and_spices'))          | 'earthlyOrigin'     | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.SAUCES))              | 'sauce'             | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.SOUPS))               | 'soup'              | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.BEVERAGES))           | 'beverage'          | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.ALCOHOLIC_BEVERAGES)) | 'alcoholicBeverage' | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.ALCOHOLIC_BEVERAGES)) | 'beverage'          | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.JUICES))              | 'juice'             | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.JUICES))              | 'beverage'          | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.TEA))                 | 'tea'               | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.TEA))                 | 'beverage'          | true  | 2
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
