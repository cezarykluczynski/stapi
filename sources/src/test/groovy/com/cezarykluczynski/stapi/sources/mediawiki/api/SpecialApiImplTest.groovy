package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector
import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class SpecialApiImplTest extends Specification {

	private static final String PAGE_1 = '''
{
    "continue": {
        "ticontinue": "278402",
        "continue": "||"
    },
    "query": {
        "normalized": [
            {
                "from": "Template:Sidebar_organization",
                "to": "Template:Sidebar organization"
            }
        ],
        "pages": {
            "244677": {
                "pageid": 244677,
                "ns": 10,
                "title": "Template:Sidebar organization",
                "transcludedin": [
                    {
                        "pageid": 407,
                        "ns": 0,
                        "title": "Federation Council"
                    },
                    {
                        "pageid": 723,
                        "ns": 0,
                        "title": "Klingon Defense Force"
                    },
                    {
                        "pageid": 2945,
                        "ns": 0,
                        "title": "Tal Shiar"
                    },
                    {
                        "pageid": 3290,
                        "ns": 0,
                        "title": "Section 31"
                    },
                    {
                        "pageid": 3477,
                        "ns": 0,
                        "title": "Military Assault Command Operations"
                    }
                ]
            }
        }
    }
}
'''
	private static final String PAGE_2 = '''
{
    "batchcomplete": "",
    "query": {
        "normalized": [
            {
                "from": "Template:Sidebar_organization",
                "to": "Template:Sidebar organization"
            }
        ],
        "pages": {
            "244677": {
                "pageid": 244677,
                "ns": 10,
                "title": "Template:Sidebar organization",
                "transcludedin": [
                    {
                        "pageid": 278402,
                        "ns": 0,
                        "title": "Immigration and Customs Enforcement"
                    },
                    {
                        "pageid": 278534,
                        "ns": 0,
                        "title": "Department of Homeland Security"
                    }
                ]
            }
        }
    }
}
'''

	BlikiConnector blikiConnectorMock

	ObjectMapper objectMapperMock

	PageHeaderConverter pageHeaderConverterMock

	SpecialApiImpl specialApiImpl

	void setup() {
		blikiConnectorMock = Mock()
		objectMapperMock = Mock()
		pageHeaderConverterMock = Mock()
		specialApiImpl = new SpecialApiImpl(blikiConnectorMock, new ObjectMapper(), new PageHeaderConverter())
	}

	void "reads transclusions"() {
		when:
		List<PageHeader> pageHeaders = specialApiImpl
				.getPagesTranscludingTemplate(TemplateTitle.SIDEBAR_ORGANIZATION, MediaWikiSource.MEMORY_ALPHA_EN)

		then:
		1 * blikiConnectorMock.getRawTemplateTransclusions(TemplateTitle.SIDEBAR_ORGANIZATION, '', MediaWikiSource.MEMORY_ALPHA_EN) >> PAGE_1
		1 * blikiConnectorMock.getRawTemplateTransclusions(TemplateTitle.SIDEBAR_ORGANIZATION, '278402', MediaWikiSource.MEMORY_ALPHA_EN) >> PAGE_2
		0 * _
		pageHeaders.size() == 7
		pageHeaders[0] == pageHeaderOf(407, 'Federation Council')
		pageHeaders[1] == pageHeaderOf(723, 'Klingon Defense Force')
		pageHeaders[2] == pageHeaderOf(2945, 'Tal Shiar')
		pageHeaders[3] == pageHeaderOf(3290, 'Section 31')
		pageHeaders[4] == pageHeaderOf(3477, 'Military Assault Command Operations')
		pageHeaders[5] == pageHeaderOf(278402, 'Immigration and Customs Enforcement')
		pageHeaders[6] == pageHeaderOf(278534, 'Department of Homeland Security')
	}

	private static PageHeader pageHeaderOf(Long pageId, String title) {
		new PageHeader(pageId: pageId, title: title, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN)
	}

}
