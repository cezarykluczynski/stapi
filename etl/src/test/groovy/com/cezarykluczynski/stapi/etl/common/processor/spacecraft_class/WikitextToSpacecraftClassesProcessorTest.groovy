package com.cezarykluczynski.stapi.etl.common.processor.spacecraft_class

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToSpacecraftClassesProcessorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String LINK_TITLE_1 = 'LINK_TITLE_1'
	private static final String LINK_TITLE_2 = 'LINK_TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private SpacecraftClassRepository spacecraftClassRepositoryMock

	private WikitextToSpacecraftClassesProcessor wikitextToSpacecraftClassesProcessor

	void setup() {
		wikitextApiMock = Mock()
		spacecraftClassRepositoryMock = Mock()
		wikitextToSpacecraftClassesProcessor = new WikitextToSpacecraftClassesProcessor(wikitextApiMock, spacecraftClassRepositoryMock)
	}

	void "given wikitext, gets links from it, then returns companies that have associated pages with matching titles"() {
		given:
		SpacecraftClass spacecraftClass = Mock()

		when:
		Set<SpacecraftClass> spacecraftClassSet = wikitextToSpacecraftClassesProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * spacecraftClassRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.of(spacecraftClass)
		1 * spacecraftClassRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		spacecraftClassSet.size() == 1
		spacecraftClassSet.contains spacecraftClass
	}

}
