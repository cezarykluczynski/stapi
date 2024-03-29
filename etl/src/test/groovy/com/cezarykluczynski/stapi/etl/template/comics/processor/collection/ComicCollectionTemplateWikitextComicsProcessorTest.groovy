package com.cezarykluczynski.stapi.etl.template.comics.processor.collection

import com.cezarykluczynski.stapi.etl.common.dto.WikitextList
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.etl.common.service.WikitextListsExtractor
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionContents
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicCollectionTemplateWikitextComicsProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String WIKITEXT_1 = 'WIKITEXT_1'
	private static final String WIKITEXT_2 = 'WIKITEXT_2'
	private static final String PAGE_TITLE_1 = 'PAGE_TITLE_1'
	private static final String PAGE_TITLE_2 = 'PAGE_TITLE_2'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageSectionExtractor pageSectionExtractorMock

	private WikitextApi wikitextApiMock

	private WikitextListsExtractor wikitextListsExtractorMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private ComicCollectionTemplateWikitextComicsProcessor comicCollectionTemplateWikitextComicsProcessor

	void setup() {
		pageSectionExtractorMock = Mock()
		wikitextApiMock = Mock()
		wikitextListsExtractorMock = Mock()
		entityLookupByNameServiceMock = Mock()
		comicCollectionTemplateWikitextComicsProcessor = new ComicCollectionTemplateWikitextComicsProcessor(pageSectionExtractorMock,
				wikitextApiMock, wikitextListsExtractorMock, entityLookupByNameServiceMock)
	}

	void "returns empty content when no section was found"() {
		given:
		Page page = new Page()

		when:
		ComicCollectionContents comicCollectionContents = comicCollectionTemplateWikitextComicsProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitles(*_) >> Lists.newArrayList()
		0 * _
		comicCollectionContents.comics.empty
		comicCollectionContents.comicSeries.empty
	}

	void "gets comics and comic series from section's wikitext"() {
		given:
		Page page = new Page()
		PageSection pageSection = new PageSection(wikitext: WIKITEXT)
		WikitextList wikitextList1 = new WikitextList(text: WIKITEXT_1)
		WikitextList wikitextList2 = new WikitextList(text: WIKITEXT_2)
		WikitextList wikitextList3 = new WikitextList(text: null)
		Comics comics = Mock()
		ComicSeries comicSeries = Mock()

		when:
		ComicCollectionContents comicCollectionContents = comicCollectionTemplateWikitextComicsProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitles(*_) >> Lists.newArrayList(pageSection)
		1 * wikitextListsExtractorMock.extractListsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList1, wikitextList2, wikitextList3)
		1 * wikitextListsExtractorMock.extractDefinitionsFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * wikitextListsExtractorMock.flattenWikitextListList(Lists.newArrayList(wikitextList1, wikitextList2, wikitextList3)) >> Lists
				.newArrayList(wikitextList1, wikitextList2, wikitextList3)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_1) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_2) >> Lists.newArrayList(PAGE_TITLE_2)
		1 * entityLookupByNameServiceMock.findComicsByName(PAGE_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.of(comics)
		1 * entityLookupByNameServiceMock.findComicsByName(PAGE_TITLE_2, MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * entityLookupByNameServiceMock.findComicSeriesByName(PAGE_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * entityLookupByNameServiceMock.findComicSeriesByName(PAGE_TITLE_2, MEDIA_WIKI_SOURCE) >> Optional.of(comicSeries)
		0 * _
		comicCollectionContents.comics.size() == 1
		comicCollectionContents.comics.contains comics
		comicCollectionContents.comicSeries.size() == 1
		comicCollectionContents.comicSeries.contains comicSeries
	}

	void "filters out 'Background information' section when there are other sections"() {
		given:
		Page page = new Page()
		PageSection pageSection = new PageSection(wikitext: WIKITEXT)
		PageSection pageSectionBackgroundInformation = new PageSection(wikitext: WIKITEXT)
		WikitextList wikitextList1 = new WikitextList(text: WIKITEXT_1)
		WikitextList wikitextList2 = new WikitextList(text: WIKITEXT_2)
		Comics comics = Mock()
		ComicSeries comicSeries = Mock()

		when:
		ComicCollectionContents comicCollectionContents = comicCollectionTemplateWikitextComicsProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitles(*_) >> Lists.newArrayList(pageSection, pageSectionBackgroundInformation)
		1 * wikitextListsExtractorMock.extractListsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList1, wikitextList2)
		1 * wikitextListsExtractorMock.extractDefinitionsFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * wikitextListsExtractorMock.flattenWikitextListList(Lists.newArrayList(wikitextList1, wikitextList2)) >> Lists
				.newArrayList(wikitextList1, wikitextList2)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_1) >> Lists.newArrayList(PAGE_TITLE_1)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_2) >> Lists.newArrayList(PAGE_TITLE_2)
		1 * entityLookupByNameServiceMock.findComicsByName(PAGE_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.of(comics)
		1 * entityLookupByNameServiceMock.findComicsByName(PAGE_TITLE_2, MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * entityLookupByNameServiceMock.findComicSeriesByName(PAGE_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * entityLookupByNameServiceMock.findComicSeriesByName(PAGE_TITLE_2, MEDIA_WIKI_SOURCE) >> Optional.of(comicSeries)
		0 * _
		comicCollectionContents.comics.size() == 1
		comicCollectionContents.comics.contains comics
		comicCollectionContents.comicSeries.size() == 1
		comicCollectionContents.comicSeries.contains comicSeries
	}

	void "returns empty contents when no links were found"() {
		Page page = new Page()
		PageSection pageSection = new PageSection(wikitext: WIKITEXT)
		PageSection pageSectionBackgroundInformation = new PageSection(wikitext: WIKITEXT)
		WikitextList wikitextList = new WikitextList(text: WIKITEXT_1)

		when:
		ComicCollectionContents comicCollectionContents = comicCollectionTemplateWikitextComicsProcessor.process(page)

		then:
		1 * pageSectionExtractorMock.findByTitles(*_) >> Lists.newArrayList(pageSection, pageSectionBackgroundInformation)
		1 * wikitextListsExtractorMock.extractListsFromWikitext(WIKITEXT) >> Lists.newArrayList(wikitextList)
		1 * wikitextListsExtractorMock.extractDefinitionsFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * wikitextListsExtractorMock.flattenWikitextListList(Lists.newArrayList(wikitextList)) >> Lists.newArrayList(wikitextList)
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT_1) >> Lists.newArrayList()
		comicCollectionContents.comics.empty
		comicCollectionContents.comicSeries.empty
	}

}
