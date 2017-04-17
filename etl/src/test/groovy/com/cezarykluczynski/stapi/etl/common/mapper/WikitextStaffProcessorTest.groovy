package com.cezarykluczynski.stapi.etl.common.mapper

import com.cezarykluczynski.stapi.etl.common.processor.WikitextStaffProcessor
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextStaffProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String PAGE_LINK_TITLE_1 = 'PAGE_LINK_TITLE_1'
	private static final String PAGE_LINK_TITLE_2 = 'PAGE_LINK_TITLE_2'
	private static final String PAGE_LINK_TITLE_3 = 'PAGE_LINK_TITLE_3'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private WikitextStaffProcessor wikitextStaffProcessor

	void setup() {
		wikitextApiMock = Mock(WikitextApi)
		entityLookupByNameServiceMock = Mock(EntityLookupByNameService)
		wikitextStaffProcessor = new WikitextStaffProcessor(wikitextApiMock, entityLookupByNameServiceMock)
	}

	void "gets staff from wikitext"() {
		given:
		Staff staff1 = Mock(Staff)
		Staff staff2 = Mock(Staff)

		when:
		Set<Staff> staffSet = wikitextStaffProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_LINK_TITLE_1, PAGE_LINK_TITLE_2, PAGE_LINK_TITLE_3)
		1 * entityLookupByNameServiceMock.findStaffByName(PAGE_LINK_TITLE_1, MEDIA_WIKI_SOURCE) >> Optional.of(staff1)
		1 * entityLookupByNameServiceMock.findStaffByName(PAGE_LINK_TITLE_2, MEDIA_WIKI_SOURCE) >> Optional.of(staff2)
		1 * entityLookupByNameServiceMock.findStaffByName(PAGE_LINK_TITLE_3, MEDIA_WIKI_SOURCE) >> Optional.empty()
		0 * _
		staffSet.size() == 2
		staffSet.contains staff1
		staffSet.contains staff2
	}

}
