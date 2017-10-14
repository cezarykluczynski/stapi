package com.cezarykluczynski.stapi.etl.animal.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.animal.creation.service.AnimalPageFilter
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class AnimalPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'
	private static final String UID = 'UID'

	private AnimalPageFilter animalPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private AnimalPageProcessor animalPageProcessor

	void setup() {
		animalPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		animalPageProcessor = new AnimalPageProcessor(animalPageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Animal animal = animalPageProcessor.process(page)

		then:
		1 * animalPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		animal == null
	}

	void "cleans title"() {
		given:
		SourcesPage page = new SourcesPage(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = new ModelPage()

		when:
		Animal animal = animalPageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		animal.name == TITLE
		animal.page == modelPage
	}

	void "page is bound"() {
		given:
		SourcesPage page = new SourcesPage(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		Animal animal = animalPageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		animal.page == modelPage
	}

	void "UID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		Animal animal = animalPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Animal) >> UID
		animal.uid == UID
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		Animal animal = animalPageProcessor.process(page)
		animal[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(animal) == trueBooleans

		where:
		page                                                                           | flagName      | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                              | 'earthInsect' | false | 0
		new SourcesPage(categories: createList(CategoryTitle.EARTH_INSECTS))           | 'earthInsect' | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_INSECTS))           | 'earthAnimal' | true  | 2
		new SourcesPage(categories: createList(CategoryTitle.EARTH_ANIMALS))           | 'earthAnimal' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.EARTH_ANIMALS_RETCONNED)) | 'earthAnimal' | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.AVIANS))                  | 'avian'       | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.CANINES))                 | 'canine'      | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.FELINES))                 | 'feline'      | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
