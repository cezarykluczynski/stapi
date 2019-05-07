package com.cezarykluczynski.stapi.sources.mediawiki.service.wikia

import spock.lang.Specification

class WikiaUrlDetectorTest extends Specification {

	private WikiaUrlDetector wikiaUrlDetector

	void setup() {
		wikiaUrlDetector = new WikiaUrlDetector()
	}

	void "detects Wikia's url"() {
		expect:
		wikiaUrlDetector.isWikiaWikiUrl('http://memory-alpha.fandom.com/api.php')
	}

	void "detects non-Wikia's url"() {
		expect:
		!wikiaUrlDetector.isWikiaWikiUrl('http://localhost/some_wiki/api.php')
	}

}
