package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
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

	private EpisodeTemplateTitleLanguagesEnrichingProcessor episodeTemplateTitleLanguagesEnrichingProcessor

	def setup() {
		wikitextApiMock = Mock(WikitextApi)
		episodeTemplateTitleLanguagesEnrichingProcessor = new EpisodeTemplateTitleLanguagesEnrichingProcessor(
				wikitextApiMock)
	}

	def "gets links from last page section and puts them into episode template"() {
		given:
		Page page = new Page(
				sections: Lists.newArrayList(
						new PageSection(
								byteOffset: 300,
								wikitext: ''
						),
						new PageSection(
								byteOffset: 500,
								wikitext: WIKITEXT
						),
						new PageSection(
								byteOffset: 100,
								wikitext: ''
						),
				)
		)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()

		when:
		episodeTemplateTitleLanguagesEnrichingProcessor.enrich(EnrichablePair.of(page, episodeTemplate))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(
				"de:${TITLE_GERMAN}".toString(),
				"it:${TITLE_ITALIAN}".toString(),
				"ja:${TITLE_JAPANESE}".toString()
		)
		episodeTemplate.titleGerman == TITLE_GERMAN
		episodeTemplate.titleItalian == TITLE_ITALIAN
		episodeTemplate.titleJapanese == TITLE_JAPANESE
	}

	@Unroll("removes prefix #prefix from japanese title with prefix")
	def "removes prefix from japanese title with prefix"() {
		given:
		Page page = new Page(
				sections: Lists.newArrayList(
						new PageSection(
								byteOffset: 500,
								wikitext: WIKITEXT
						)
				)
		)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()
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

	def "tolerates empty section list"() {
		given:
		Page page = new Page(sections: Lists.newArrayList())
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()

		when:
		episodeTemplateTitleLanguagesEnrichingProcessor.enrich(EnrichablePair.of(page, episodeTemplate))

		then:
		0 * _
		notThrown(Throwable)
		episodeTemplate.titleGerman == null
		episodeTemplate.titleItalian == null
		episodeTemplate.titleJapanese == null
	}


}
