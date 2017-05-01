package com.cezarykluczynski.stapi.server.common.controller

import spock.lang.Specification

class WebAppControllerTest extends Specification {

	private WebAppController webAppController

	void setup() {
		webAppController = new WebAppController()
	}

	void "returns main template name"() {
		when:
		String templateName = webAppController.index()

		then:
		templateName == 'index'
	}

}
