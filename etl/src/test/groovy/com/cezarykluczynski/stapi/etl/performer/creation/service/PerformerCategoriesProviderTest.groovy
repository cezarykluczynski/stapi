package com.cezarykluczynski.stapi.etl.performer.creation.service

import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import org.assertj.core.util.Lists
import spock.lang.Specification

class PerformerCategoriesProviderTest extends Specification {

	private static final String CATEGORY_TITLE_1 = 'CATEGORY_TITLE_1'
	private static final String CATEGORY_TITLE_2 = 'CATEGORY_TITLE_2'

	private CategoryApi categoryApiMock
	private PerformerCategoriesProvider performerCategoriesProvider

	void setup() {
		categoryApiMock = Mock()
		performerCategoriesProvider = new PerformerCategoriesProvider(categoryApiMock)
	}

	void "gets performer categories once"() {
		given:
		CategoryHeader categoryHeader1 = new CategoryHeader(title: CATEGORY_TITLE_1)
		CategoryHeader categoryHeader2 = new CategoryHeader(title: CATEGORY_TITLE_2)
		List<CategoryHeader> categoryHeaders = Lists.newArrayList(categoryHeader1, categoryHeader2)

		when:
		List<String> categoryHeadersResponse = performerCategoriesProvider.provide()

		then:
		1 * categoryApiMock.getCategoriesInCategoryIncludingSubcategories(CategoryTitle.PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				categoryHeaders
		0 * _
		categoryHeadersResponse == [CategoryTitle.PERFORMERS, CATEGORY_TITLE_1, CATEGORY_TITLE_2]

		when:
		categoryHeadersResponse = performerCategoriesProvider.provide()

		then:
		0 * _
		categoryHeadersResponse == [CategoryTitle.PERFORMERS, CATEGORY_TITLE_1, CATEGORY_TITLE_2]
	}

}
