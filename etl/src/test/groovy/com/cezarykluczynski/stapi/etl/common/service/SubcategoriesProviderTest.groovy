package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class SubcategoriesProviderTest extends Specification {

	private static final String CATEGORY_TITLE_1 = 'CATEGORY_TITLE_1'
	private static final String CATEGORY_TITLE_2 = 'CATEGORY_TITLE_2'

	private CategoryApi categoryApiMock
	private SubcategoriesProvider performerCategoriesProvider

	void setup() {
		categoryApiMock = Mock()
		performerCategoriesProvider = new SubcategoriesProvider(categoryApiMock)
	}

	void "gets performer categories once"() {
		given:
		CategoryHeader categoryHeader1 = new CategoryHeader(title: CATEGORY_TITLE_1)
		CategoryHeader categoryHeader2 = new CategoryHeader(title: CATEGORY_TITLE_2)
		List<CategoryHeader> categoryHeaders = Lists.newArrayList(categoryHeader1, categoryHeader2)

		when:
		List<String> categoryHeadersResponse = performerCategoriesProvider.provideSubcategories(CategoryTitle.PERFORMERS)

		then:
		1 * categoryApiMock.getCategoriesInCategoryIncludingSubcategories(CategoryTitle.PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN,
				Lists.newArrayList()) >> categoryHeaders
		0 * _
		categoryHeadersResponse == [CategoryTitle.PERFORMERS, CATEGORY_TITLE_1, CATEGORY_TITLE_2]

		when:
		categoryHeadersResponse = performerCategoriesProvider.provideSubcategories(CategoryTitle.PERFORMERS)

		then:
		0 * _
		categoryHeadersResponse == [CategoryTitle.PERFORMERS, CATEGORY_TITLE_1, CATEGORY_TITLE_2]
	}

}
