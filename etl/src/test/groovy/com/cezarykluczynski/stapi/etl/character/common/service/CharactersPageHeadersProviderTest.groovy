package com.cezarykluczynski.stapi.etl.character.common.service

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader

class CharactersPageHeadersProviderTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_INDIVIDUALS = 'TITLE_INDIVIDUALS'
	private static final String TITLE_MILITARY_PERSONNEL = 'TITLE_MILITARY_PERSONNEL'
	private static final String TITLE_Q_CONTINUUM = 'TITLE_Q_CONTINUUM'
	private static final String TITLE_STARFLEET_PERSONNEL = 'TITLE_STARFLEET_PERSONNEL'
	private static final String TITLE_EDUCATORS = 'TITLE_EDUCATORS'
	private static final String TITLE_ENTERTAINERS = 'TITLE_ENTERTAINERS'
	private static final String TITLE_GOVERNMENT_OFFICIALS = 'TITLE_GOVERNMENT_OFFICIALS'
	private static final String TITLE_SCIENTISTS = 'TITLE_SCIENTISTS'
	private static final String TITLE_HOLOGRAMS = 'TITLE_HOLOGRAMS'
	private static final String TITLE_HOLOGRAPHIC_DUPLICATES = 'TITLE_HOLOGRAPHIC_DUPLICATES'
	private static final String TITLE_FICTIONAL_CHARACTERS = 'TITLE_FICTIONAL_CHARACTERS'
	private static final String TITLE_THE_DIXON_HILL_SERIES_CHARACTERS = 'TITLE_THE_DIXON_HILL_SERIES_CHARACTERS'
	private static final String TITLE_SHAKESPEARE_CHARACTERS = 'TITLE_SHAKESPEARE_CHARACTERS'
	private static final String TITLE_ARTISTS = 'TITLE_ARTISTS'
	private static final String TITLE_ATHLETES = 'TITLE_ATHLETES'
	private static final String TITLE_AUTHORS = 'TITLE_AUTHORS'
	private static final String TITLE_DABO_GIRLS = 'TITLE_DABO_GIRLS'
	private static final String TITLE_MUSICIANS = 'TITLE_MUSICIANS'
	private static final String TITLE_INDIVIDUAL_ANIMALS = 'TITLE_INDIVIDUAL_ANIMALS'

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
		1 * categoryApiMock.getPagesIncludingSubcategoriesExcluding(CategoryTitle.INDIVIDUALS, CharactersPageHeadersProvider.EXCLUDES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_INDIVIDUALS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.MILITARY_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MILITARY_PERSONNEL)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.Q_CONTINUUM, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_Q_CONTINUUM)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.STARFLEET_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_STARFLEET_PERSONNEL)
		1 * categoryApiMock.getPages(CategoryTitle.HOLOGRAMS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_HOLOGRAMS)
		1 * categoryApiMock.getPages(CategoryTitle.HOLOGRAPHIC_DUPLICATES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_HOLOGRAPHIC_DUPLICATES)
		1 * categoryApiMock.getPages(CategoryTitle.FICTIONAL_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_FICTIONAL_CHARACTERS)
		1 * categoryApiMock.getPages(CategoryTitle.THE_DIXON_HILL_SERIES_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_THE_DIXON_HILL_SERIES_CHARACTERS)
		1 * categoryApiMock.getPages(CategoryTitle.SHAKESPEARE_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SHAKESPEARE_CHARACTERS)
		1 * categoryApiMock.getPages(CategoryTitle.ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ARTISTS)
		1 * categoryApiMock.getPages(CategoryTitle.ATHLETES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ATHLETES)
		1 * categoryApiMock.getPages(CategoryTitle.AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_AUTHORS)
		1 * categoryApiMock.getPages(CategoryTitle.DABO_GIRLS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_DABO_GIRLS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.EDUCATORS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EDUCATORS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.ENTERTAINERS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ENTERTAINERS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.GOVERNMENT_OFFICIALS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_GOVERNMENT_OFFICIALS)
		1 * categoryApiMock.getPages(CategoryTitle.MUSICIANS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MUSICIANS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.SCIENTISTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SCIENTISTS)
		1 * categoryApiMock.getPages(CategoryTitle.INDIVIDUAL_ANIMALS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_INDIVIDUAL_ANIMALS)
		0 * _

		then:
		categoryHeaderTitleList.contains TITLE_INDIVIDUALS
		categoryHeaderTitleList.contains TITLE_MILITARY_PERSONNEL
		categoryHeaderTitleList.contains TITLE_Q_CONTINUUM
		categoryHeaderTitleList.contains TITLE_STARFLEET_PERSONNEL
		categoryHeaderTitleList.contains TITLE_EDUCATORS
		categoryHeaderTitleList.contains TITLE_ENTERTAINERS
		categoryHeaderTitleList.contains TITLE_GOVERNMENT_OFFICIALS
		categoryHeaderTitleList.contains TITLE_SCIENTISTS
		categoryHeaderTitleList.contains TITLE_HOLOGRAMS
		categoryHeaderTitleList.contains TITLE_HOLOGRAPHIC_DUPLICATES
		categoryHeaderTitleList.contains TITLE_FICTIONAL_CHARACTERS
		categoryHeaderTitleList.contains TITLE_THE_DIXON_HILL_SERIES_CHARACTERS
		categoryHeaderTitleList.contains TITLE_SHAKESPEARE_CHARACTERS
		categoryHeaderTitleList.contains TITLE_ARTISTS
		categoryHeaderTitleList.contains TITLE_ATHLETES
		categoryHeaderTitleList.contains TITLE_AUTHORS
		categoryHeaderTitleList.contains TITLE_DABO_GIRLS
		categoryHeaderTitleList.contains TITLE_MUSICIANS
		categoryHeaderTitleList.contains TITLE_INDIVIDUAL_ANIMALS

		when:
		List<PageHeader> anotherPageHeaderList = charactersPageHeadersProvider.provide()

		then:
		0 * _

		then:
		anotherPageHeaderList == pageHeaderList
	}

}
