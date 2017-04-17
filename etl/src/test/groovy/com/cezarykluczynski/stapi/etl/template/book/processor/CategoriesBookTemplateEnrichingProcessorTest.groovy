package com.cezarykluczynski.stapi.etl.template.book.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class CategoriesBookTemplateEnrichingProcessorTest extends Specification {

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private CategoriesBookTemplateEnrichingProcessor categoriesBookTemplateEnrichingProcessor

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		categoriesBookTemplateEnrichingProcessor = new CategoriesBookTemplateEnrichingProcessor(categoryTitlesExtractingProcessorMock)
	}

	@Unroll('set #flagName flag when #categoryHeaderList is passed; expect #trueBooleans not null fields')
	void "sets flagName when category header list is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> { List<CategoryHeader> categoryHeaderList ->
			Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		BookTemplate bookTemplate = new BookTemplate()
		categoriesBookTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, bookTemplate))
		bookTemplate[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(bookTemplate) == trueBooleans

		where:
		categoryHeaderList                           | flagName          | flag  | trueBooleans
		Lists.newArrayList()                         | 'novel'           | false | 0
		createList(CategoryTitle.NOVELS)             | 'novel'           | true  | 1
		createList(CategoryTitle.REFERENCE_BOOKS)    | 'referenceBook'   | true  | 1
		createList(CategoryTitle.BIOGRAPHY_BOOKS)    | 'biographyBook'   | true  | 1
		createList(CategoryTitle.ROLE_PLAYING_GAMES) | 'rolePlayingBook' | true  | 1
		createList(CategoryTitle.E_BOOKS)            | 'eBook'           | true  | 1
		createList(CategoryTitle.ANTHOLOGIES)        | 'anthology'       | true  | 1
		createList(CategoryTitle.NOVELIZATIONS)      | 'novelization'    | true  | 1
		createList(CategoryTitle.AUDIOBOOKS)         | 'audiobook'       | true  | 1
	}

	void "sets audiobook abridged flag to false"() {
		given:
		BookTemplate bookTemplate = Mock()

		when:
		categoriesBookTemplateEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(), bookTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * bookTemplate.setAudiobookAbridged(false)
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
