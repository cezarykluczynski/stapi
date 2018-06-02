package com.cezarykluczynski.stapi.etl.template.planet.processor

import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class AstronomicalObjectWikitextProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessor

	void setup() {
		wikitextApiMock = Mock()
		astronomicalObjectWikitextProcessor = new AstronomicalObjectWikitextProcessor(wikitextApiMock)
	}

	@SuppressWarnings('LineLength')
	@Unroll('when #pageLink is found in wikitext, #astronomicalObjectType is returned')
	void "when links are found, they are processed"() {
		given:
		wikitextApiMock.getPageLinksFromWikitext(_) >> pageLinkList

		expect:
		astronomicalObjectType == astronomicalObjectWikitextProcessor.process(wikitext)

		where:
		pageLinkList                                                                                | astronomicalObjectType                  | wikitext
		Lists.newArrayList()                                                                        | null                                    | WIKITEXT
		Lists.newArrayList(new PageLink())                                                          | null                                    | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.ROGUE_PLANET))   | AstronomicalObjectType.ROGUE_PLANET     | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.M_CLASS_1))      | AstronomicalObjectType.M_CLASS_PLANET   | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.M_CLASS_2))      | AstronomicalObjectType.M_CLASS_PLANET   | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.CLASS_M))        | AstronomicalObjectType.M_CLASS_PLANET   | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.CLASS_M))        | AstronomicalObjectType.PLANET           | AstronomicalObjectWikitextProcessor.NON
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.CLASS_M))        | AstronomicalObjectType.PLANET           | AstronomicalObjectWikitextProcessor.FORMERLY
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.CLASS_D))        | AstronomicalObjectType.D_CLASS_PLANET   | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.CLASS_H))        | AstronomicalObjectType.H_CLASS_PLANET   | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.CLASS_L))        | AstronomicalObjectType.L_CLASS_PLANET   | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.CLASS_K_PLANET)) | AstronomicalObjectType.K_CLASS_PLANET   | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.CLASS_Y_PLANET)) | AstronomicalObjectType.Y_CLASS_PLANET   | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.GAS_GIANT))      | AstronomicalObjectType.GAS_GIANT_PLANET | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.PLANETOID))      | AstronomicalObjectType.PLANETOID        | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.MOON))           | AstronomicalObjectType.MOON             | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.CLASS_4_MOON))   | AstronomicalObjectType.MOON             | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.PLANET))         | AstronomicalObjectType.PLANET           | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.ASTEROID))       | AstronomicalObjectType.ASTEROID         | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.STAR))           | AstronomicalObjectType.STAR             | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.BINARY_SUN))     | AstronomicalObjectType.STAR             | WIKITEXT
		Lists.newArrayList(new PageLink(title: AstronomicalObjectWikitextProcessor.PROTOPLANET))    | AstronomicalObjectType.PLANET           | WIKITEXT
	}

}
