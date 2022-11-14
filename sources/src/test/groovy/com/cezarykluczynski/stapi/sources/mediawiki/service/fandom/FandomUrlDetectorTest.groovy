package com.cezarykluczynski.stapi.sources.mediawiki.service.fandom

import spock.lang.Specification

class FandomUrlDetectorTest extends Specification {

	private FandomUrlDetector fandomUrlDetector

	void setup() {
		fandomUrlDetector = new FandomUrlDetector()
	}

	void "detects Fandom's url"() {
		expect:
		fandomUrlDetector.isFandomWikiUrl('http://memory-alpha.fandom.com/api.php')
	}

	void "detects non-Fandom's url"() {
		expect:
		!fandomUrlDetector.isFandomWikiUrl('http://localhost/some_wiki/api.php')
	}

}
