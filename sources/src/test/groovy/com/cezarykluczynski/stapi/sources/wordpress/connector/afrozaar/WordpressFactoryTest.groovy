package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar

import com.afrozaar.wordpress.wpapi.v2.Client
import com.afrozaar.wordpress.wpapi.v2.Wordpress
import spock.lang.Specification

class WordpressFactoryTest extends Specification {

	private static final String BASE_URL = 'BASE_URL'

	private WordpressFactory wordpressFactory

	void setup() {
		wordpressFactory = new WordpressFactory()
	}

	void "creates client"() {
		when:
		Wordpress wordpress = wordpressFactory.createForUrl(BASE_URL)

		then:
		wordpress instanceof Client
		((Client) wordpress).baseUrl == BASE_URL
	}

}
