package com.cezarykluczynski.stapi.etl.common.service

import org.jsoup.nodes.Document
import spock.lang.Specification

class JsoupParserTest extends Specification {

	private static final String HTML = '<html><head></head><body><div class="test"></div></body></html>'

	private JsoupParser jsoupParser

	void setup() {
		jsoupParser = new JsoupParser()
	}

	void "parses HTML"() {
		when:
		Document document = jsoupParser.parse(HTML)

		then:
		document != null
		document.getElementsByClass('test').size() == 1
	}

}
