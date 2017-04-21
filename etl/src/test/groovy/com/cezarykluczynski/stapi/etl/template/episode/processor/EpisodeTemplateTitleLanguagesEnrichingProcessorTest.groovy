package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class EpisodeTemplateTitleLanguagesEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String TITLE_GERMAN = 'TITLE_GERMAN'
	private static final String TITLE_ITALIAN = 'TITLE_ITALIAN'
	private static final String TITLE_JAPANESE = 'TITLE_JAPANESE'

	private WikitextApi wikitextApiMock

	private PageSectionExtractor pageSectionExtractorMock

	private EpisodeTemplateTitleLanguagesEnrichingProcessor episodeTemplateTitleLanguagesEnrichingProcessor

	void setup() {
		wikitextApiMock = Mock()
		pageSectionExtractorMock = Mock()
		episodeTemplateTitleLanguagesEnrichingProcessor = new EpisodeTemplateTitleLanguagesEnrichingProcessor(
				wikitextApiMock, pageSectionExtractorMock)
	}

	void "gets links from last page section and puts them into episode template"() {
		given:
		PageSection pageSection = new PageSection(wikitext: WIKITEXT)
		List<PageSection> pageSectionList = Lists.newArrayList(pageSection)
		Page page = new Page(sections: pageSectionList)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()

		when:
		episodeTemplateTitleLanguagesEnrichingProcessor.enrich(EnrichablePair.of(page, episodeTemplate))

		then:
		1 * pageSectionExtractorMock.extractLastPageSection(page) >> pageSection
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(
				"de:${TITLE_GERMAN}".toString(),
				"it:${TITLE_ITALIAN}".toString(),
				"ja:${TITLE_JAPANESE}".toString()
		)
		episodeTemplate.titleGerman == TITLE_GERMAN
		episodeTemplate.titleItalian == TITLE_ITALIAN
		episodeTemplate.titleJapanese == TITLE_JAPANESE
	}

	@Unroll('removes prefix #prefix from japanese title with prefix')
	void "removes prefix from japanese title with prefix"() {
		given:
		PageSection pageSection = new PageSection(wikitext: WIKITEXT)
		List<PageSection> pageSectionList = Lists.newArrayList(pageSection)
		Page page = new Page(sections: pageSectionList)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()
		pageSectionExtractorMock.extractLastPageSection(page) >> pageSection
		wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(
				"ja:${prefix}${TITLE_JAPANESE}".toString())

		expect:
		episodeTemplateTitleLanguagesEnrichingProcessor.enrich(EnrichablePair.of(page, episodeTemplate))
		episodeTemplate.titleJapanese == title

		where:
		prefix                                                                      | title
		EpisodeTemplateTitleLanguagesEnrichingProcessor.JAPANESE_SERIES_PREFIXES[0] | TITLE_JAPANESE
		EpisodeTemplateTitleLanguagesEnrichingProcessor.JAPANESE_SERIES_PREFIXES[1] | TITLE_JAPANESE
		EpisodeTemplateTitleLanguagesEnrichingProcessor.JAPANESE_SERIES_PREFIXES[2] | TITLE_JAPANESE
		EpisodeTemplateTitleLanguagesEnrichingProcessor.JAPANESE_SERIES_PREFIXES[3] | TITLE_JAPANESE
		EpisodeTemplateTitleLanguagesEnrichingProcessor.JAPANESE_SERIES_PREFIXES[4] | TITLE_JAPANESE
		EpisodeTemplateTitleLanguagesEnrichingProcessor.JAPANESE_SERIES_PREFIXES[5] | TITLE_JAPANESE
	}

	void "tolerates empty section list"() {
		given:
		Page page = new Page(sections: Lists.newArrayList())
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()

		when:
		episodeTemplateTitleLanguagesEnrichingProcessor.enrich(EnrichablePair.of(page, episodeTemplate))

		then:
		1 * pageSectionExtractorMock.extractLastPageSection(page) >> null
		0 * _
		notThrown(Throwable)
		episodeTemplate.titleGerman == null
		episodeTemplate.titleItalian == null
		episodeTemplate.titleJapanese == null
	}

}
