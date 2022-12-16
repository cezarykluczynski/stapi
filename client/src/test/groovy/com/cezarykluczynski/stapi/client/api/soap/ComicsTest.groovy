package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicsPortType
import spock.lang.Specification

class ComicsTest extends Specification {

	private ComicsPortType comicsPortTypeMock

	private Comics comics

	void setup() {
		comicsPortTypeMock = Mock()
		comics = new Comics(comicsPortTypeMock)
	}

	void "gets single entity"() {
		given:
		ComicsBaseRequest comicsBaseRequest = Mock()
		ComicsBaseResponse comicsBaseResponse = Mock()

		when:
		ComicsBaseResponse comicsBaseResponseOutput = comics.search(comicsBaseRequest)

		then:
		1 * comicsPortTypeMock.getComicsBase(comicsBaseRequest) >> comicsBaseResponse
		0 * _
		comicsBaseResponse == comicsBaseResponseOutput
	}

	void "searches entities"() {
		given:
		ComicsFullRequest comicsFullRequest = Mock()
		ComicsFullResponse comicsFullResponse = Mock()

		when:
		ComicsFullResponse comicsFullResponseOutput = comics.get(comicsFullRequest)

		then:
		1 * comicsPortTypeMock.getComicsFull(comicsFullRequest) >> comicsFullResponse
		0 * _
		comicsFullResponse == comicsFullResponseOutput
	}

}
