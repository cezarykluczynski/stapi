package com.cezarykluczynski.stapi.server.common.controller

import org.springframework.http.HttpEntity
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

	void "returns terms of service template name"() {
		when:
		String templateName = webAppController.termsOfService()

		then:
		templateName == 'termsOfService'
	}

	void "returns privacy policy template name"() {
		when:
		String templateName = webAppController.privacyPolicy()

		then:
		templateName == 'privacyPolicy'
	}

	void "returns robots.txt response"() {
		when:
		HttpEntity<String> httpEntity = webAppController.robotsTxt()

		then:
		httpEntity.headers.get('Content-Type').get(0) == 'text/plain'
		httpEntity.body.contains('User-agent: *')
		httpEntity.body.contains('Allow: /')
	}

}
