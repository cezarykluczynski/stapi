package com.cezarykluczynski.stapi.etl.character.common.service

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader

class CharactersPageHeadersProviderTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_INDIVIDUALS = 'TITLE_INDIVIDUALS'
	private static final String TITLE_STARFLEET_PERSONNEL = 'TITLE_STARFLEET_PERSONNEL'
	private static final String TITLE_GOVERNMENT_OFFICIALS = 'TITLE_GOVERNMENT_OFFICIALS'
	private static final String TITLE_SCIENTISTS = 'TITLE_SCIENTISTS'
	private static final String TITLE_MONARCHS = 'TITLE_MONARCHS'

	private CategoryApi categoryApiMock

	private CharactersPageHeadersProvider charactersPageHeadersProvider

	void setup() {
		categoryApiMock = Mock()
		charactersPageHeadersProvider = new CharactersPageHeadersProvider(categoryApiMock)
	}

	@SuppressWarnings('LineLength')
	void "CharactersPageHeadersProvider provides list every time, but calls api only the first time"() {
		when:
		List<PageHeader> pageHeaderList = charactersPageHeadersProvider.provide()
		List<String> categoryHeaderTitleList = pageHeaderListToPageTitleList(pageHeaderList)

		then:
		1 * categoryApiMock.getPagesIncludingSubcategoriesExcluding(CategoryTitle.INDIVIDUALS, CharactersPageHeadersProvider.EXCLUDES, 3, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_INDIVIDUALS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.STARFLEET_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_STARFLEET_PERSONNEL)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.GOVERNMENT_OFFICIALS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_GOVERNMENT_OFFICIALS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.SCIENTISTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SCIENTISTS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.MONARCHS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MONARCHS)
		0 * _

		then:
		categoryHeaderTitleList.contains TITLE_INDIVIDUALS
		categoryHeaderTitleList.contains TITLE_STARFLEET_PERSONNEL
		categoryHeaderTitleList.contains TITLE_GOVERNMENT_OFFICIALS
		categoryHeaderTitleList.contains TITLE_SCIENTISTS
		categoryHeaderTitleList.contains TITLE_MONARCHS

		when:
		List<PageHeader> anotherPageHeaderList = charactersPageHeadersProvider.provide()

		then:
		0 * _

		then:
		anotherPageHeaderList == pageHeaderList
	}

}
