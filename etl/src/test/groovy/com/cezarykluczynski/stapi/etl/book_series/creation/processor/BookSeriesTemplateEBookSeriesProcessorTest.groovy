package com.cezarykluczynski.stapi.etl.book_series.creation.processor

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.book_series.processor.BookSeriesTemplateEBookSeriesProcessor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class BookSeriesTemplateEBookSeriesProcessorTest extends Specification {

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private BookSeriesTemplateEBookSeriesProcessor bookSeriesTemplateEBookSeriesProcessor

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		bookSeriesTemplateEBookSeriesProcessor = new BookSeriesTemplateEBookSeriesProcessor(categoryTitlesExtractingProcessorMock)
	}

	void "returns true when Page has 'EBook series' category"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		Page page = new Page(categories: categoryHeaderList)

		when:
		Boolean photonovel = bookSeriesTemplateEBookSeriesProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList(categoryHeader)) >> Lists.newArrayList(CategoryTitle.E_BOOK_SERIES)
		0 * _
		photonovel
	}

	void "returns true when Page does not have 'EBook series' category"() {
		given:
		Page page = new Page()

		when:
		Boolean photonovel = bookSeriesTemplateEBookSeriesProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		0 * _
		!photonovel
	}

}

