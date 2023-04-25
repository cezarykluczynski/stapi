package com.cezarykluczynski.stapi.etl.title.creation.service

import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApiImpl
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class RanksTemplateServiceTest extends Specification {

	private static final String TEMPLATE_TEXT = '''
		| align="left" | Fleet:
		| colspan="2" | [[crewman|CRM]]
		| colspan="2" | [[petty officer|PO]]
		| align="left" | Military:
		| colspan="2" | [[private|PVT]]<br />[[airman|AMN]]
		| [[corporal|CPL]]
		| align="left" | Services:
		| colspan="20" | [[Andorian Imperial Guard ranks|Andorian]]'''

	private PageApi pageApiMock

	private RanksTemplateService ranksTemplateService

	void setup() {
		pageApiMock = Mock()
		ranksTemplateService = new RanksTemplateService(pageApiMock, new WikitextApiImpl())
	}

	void "parses template wikitext, then tells which page titles were found and which were not"() {
		given:
		Page page = new Page(wikitext: TEMPLATE_TEXT)

		when:
		boolean isFleetRank = ranksTemplateService.isFleetRank('Crewman')

		then:
		1 * pageApiMock.getTemplate(TemplateTitle.RANKS, MediaWikiSource.MEMORY_ALPHA_EN) >> page
		0 * _

		then:
		0 * _
		isFleetRank
		ranksTemplateService.isMilitaryRank('Private')

		then:
		0 * _
		!ranksTemplateService.isFleetRank('airman')
		!ranksTemplateService.isMilitaryRank('Captain')
	}

}
