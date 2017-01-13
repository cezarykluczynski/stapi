package com.cezarykluczynski.stapi.sources.mediawiki.service.wikia

import spock.lang.Specification

class WikiaUrlDetectorTest extends Specification {

	private WikiaUrlDetector wikiaUrlDetector

	def setup() {
		wikiaUrlDetector = new WikiaUrlDetector()
	}

	def "detects Wikia's url"() {
		expect:
		wikiaUrlDetector.isWikiaWikiUrl('http://memory-alpha.wikia.com/api.php')
	}

	def "detects non-Wikia's url"() {
		expect:
		!wikiaUrlDetector.isWikiaWikiUrl('http://localhost/some_wiki/api.php')
	}


}
