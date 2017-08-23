package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.spacecraft_type.repository.SpacecraftTypeRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import info.bliki.api.PageInfo
import spock.lang.Specification

class StarshipClassSpacecraftTypeProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String PAGE_TITLE_1 = 'PAGE_TITLE_1'
	private static final String PAGE_TITLE_1_TO_BE_CORRECTED_LOWER_CASE = 'pAGE_TITLE_1_TO_BE_CORRECTED'
	private static final String PAGE_TITLE_1_TO_BE_CORRECTED_UPPER_CASE = 'PAGE_TITLE_1_TO_BE_CORRECTED'
	private static final String PAGE_TITLE_2 = 'PAGE_TITLE_2'
	private static final String PAGE_ID_STRING = '456'
	private static final Long PAGE_ID_LONG = 456L
	private static final MediaWikiSource MODEL_MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource SOURCES_MEDIA_WIKI_SOURCE =
			com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN

	private WikitextApi wikitextApiMock

	private StarshipClassTemplateNameCorrectionFixedValueProvider starshipClassTemplateNameCorrectionFixedValueProviderMock

	private PageApi pageApiMock

	private SpacecraftTypeRepository spacecraftTypeRepositoryMock

	private StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessor

	void setup() {
		wikitextApiMock = Mock()
		starshipClassTemplateNameCorrectionFixedValueProviderMock = Mock()
		pageApiMock = Mock()
		spacecraftTypeRepositoryMock = Mock()
		starshipClassSpacecraftTypeProcessor = new StarshipClassSpacecraftTypeProcessor(wikitextApiMock,
				starshipClassTemplateNameCorrectionFixedValueProviderMock, pageApiMock, spacecraftTypeRepositoryMock)
	}

	void "when spacecraft type is found by name, it is used"() {
		given:
		SpacecraftType spacecraftType = Mock()

		when:
		Set<SpacecraftType> spacecraftTypeSet = starshipClassSpacecraftTypeProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * starshipClassTemplateNameCorrectionFixedValueProviderMock.getSearchedValue(PAGE_TITLE_1) >> FixedValueHolder.empty()
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PAGE_TITLE_1) >> Optional.of(spacecraftType)
		0 * _
		spacecraftTypeSet.size() == 1
		spacecraftTypeSet.contains spacecraftType
	}

	void "title is corrected before using"() {
		given:
		SpacecraftType spacecraftType = Mock()

		when:
		Set<SpacecraftType> spacecraftTypeSet = starshipClassSpacecraftTypeProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1_TO_BE_CORRECTED_LOWER_CASE)
		1 * starshipClassTemplateNameCorrectionFixedValueProviderMock.getSearchedValue(PAGE_TITLE_1_TO_BE_CORRECTED_UPPER_CASE) >>
				FixedValueHolder.found(PAGE_TITLE_1)
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PAGE_TITLE_1) >> Optional.of(spacecraftType)
		0 * _
		spacecraftTypeSet.size() == 1
		spacecraftTypeSet.contains spacecraftType
	}

	void "when spacecraft type is not found by name, it is found by canonical page title"() {
		given:
		SpacecraftType spacecraftType = Mock()

		when:
		Set<SpacecraftType> spacecraftTypeSet = starshipClassSpacecraftTypeProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * starshipClassTemplateNameCorrectionFixedValueProviderMock.getSearchedValue(PAGE_TITLE_1) >> FixedValueHolder.empty()
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PAGE_TITLE_1) >> Optional.empty()
		1 * spacecraftTypeRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_1, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(spacecraftType)
		0 * _
		spacecraftTypeSet.size() == 1
		spacecraftTypeSet.contains spacecraftType
	}

	void "when spacecraft type is not found by name nor canonical page title, API is used to get redirect target page"() {
		given:
		SpacecraftType spacecraftType = Mock()
		Page page = new Page(title: PAGE_TITLE_2)
		PageInfo pageInfo = new PageInfo(pageid: PAGE_ID_STRING)

		when:
		Set<SpacecraftType> spacecraftTypeSet = starshipClassSpacecraftTypeProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * starshipClassTemplateNameCorrectionFixedValueProviderMock.getSearchedValue(PAGE_TITLE_1) >> FixedValueHolder.empty()
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PAGE_TITLE_1) >> Optional.empty()
		1 * spacecraftTypeRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_1, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_1, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * pageApiMock.getPageInfo(PAGE_TITLE_2, SOURCES_MEDIA_WIKI_SOURCE) >> pageInfo
		1 * spacecraftTypeRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID_LONG, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(spacecraftType)
		0 * _
		spacecraftTypeSet.size() == 1
		spacecraftTypeSet.contains spacecraftType
	}

	void "when spacecraft type is not found by name nor canonical page title, and no page is found, empty set is returned"() {
		when:
		Set<SpacecraftType> spacecraftTypeSet = starshipClassSpacecraftTypeProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * starshipClassTemplateNameCorrectionFixedValueProviderMock.getSearchedValue(PAGE_TITLE_1) >> FixedValueHolder.empty()
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PAGE_TITLE_1) >> Optional.empty()
		1 * spacecraftTypeRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_1, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_1, SOURCES_MEDIA_WIKI_SOURCE) >> null
		0 * _
		spacecraftTypeSet.empty
	}

	void "when spacecraft type is not found by name nor canonical page title, and no page info is found, empty set is returned"() {
		given:
		Page page = new Page(title: PAGE_TITLE_2)

		when:
		Set<SpacecraftType> spacecraftTypeSet = starshipClassSpacecraftTypeProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * starshipClassTemplateNameCorrectionFixedValueProviderMock.getSearchedValue(PAGE_TITLE_1) >> FixedValueHolder.empty()
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PAGE_TITLE_1) >> Optional.empty()
		1 * spacecraftTypeRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_1, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_1, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * pageApiMock.getPageInfo(PAGE_TITLE_2, SOURCES_MEDIA_WIKI_SOURCE) >> null
		0 * _
		spacecraftTypeSet.empty
	}

	void "when spacecraft type is not found by name, canonical page title, anor target page title, empty set is returned"() {
		given:
		Page page = new Page(title: PAGE_TITLE_2)
		PageInfo pageInfo = new PageInfo(pageid: PAGE_ID_STRING)

		when:
		Set<SpacecraftType> spacecraftTypeSet = starshipClassSpacecraftTypeProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * starshipClassTemplateNameCorrectionFixedValueProviderMock.getSearchedValue(PAGE_TITLE_1) >> FixedValueHolder.empty()
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PAGE_TITLE_1) >> Optional.empty()
		1 * spacecraftTypeRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_1, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_1, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * pageApiMock.getPageInfo(PAGE_TITLE_2, SOURCES_MEDIA_WIKI_SOURCE) >> pageInfo
		1 * spacecraftTypeRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID_LONG, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		0 * _
		spacecraftTypeSet.empty
	}

}
