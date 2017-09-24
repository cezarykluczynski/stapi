package com.cezarykluczynski.stapi.etl.title.creation.service

import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApiImpl
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
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
		| align="left" | Positions:
		| colspan="7" align="justify" | [[Technician]] &bull; [[Specialist]] &bull; [[Chief]]
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
		ranksTemplateService.postConstruct()

		then:
		pageApiMock.getPage('Template:' + TemplateTitle.RANKS, MediaWikiSource.MEMORY_ALPHA_EN) >> page

		then:
		ranksTemplateService.isFleetRank('Crewman')
		ranksTemplateService.isMilitaryRank('Private')
		ranksTemplateService.isPosition('technician')

		then:
		!ranksTemplateService.isFleetRank('airman')
		!ranksTemplateService.isMilitaryRank('Captain')
		!ranksTemplateService.isPosition('Andorian Imperial Guard ranks')
	}

}
