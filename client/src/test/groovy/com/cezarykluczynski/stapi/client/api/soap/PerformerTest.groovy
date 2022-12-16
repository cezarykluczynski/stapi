package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType
import spock.lang.Specification

class PerformerTest extends Specification {

	private PerformerPortType performerPortTypeMock

	private Performer performer

	void setup() {
		performerPortTypeMock = Mock()
		performer = new Performer(performerPortTypeMock)
	}

	void "gets single entity"() {
		given:
		PerformerBaseRequest performerBaseRequest = Mock()
		PerformerBaseResponse performerBaseResponse = Mock()

		when:
		PerformerBaseResponse performerBaseResponseOutput = performer.search(performerBaseRequest)

		then:
		1 * performerPortTypeMock.getPerformerBase(performerBaseRequest) >> performerBaseResponse
		0 * _
		performerBaseResponse == performerBaseResponseOutput
	}

	void "searches entities"() {
		given:
		PerformerFullRequest performerFullRequest = Mock()
		PerformerFullResponse performerFullResponse = Mock()

		when:
		PerformerFullResponse performerFullResponseOutput = performer.get(performerFullRequest)

		then:
		1 * performerPortTypeMock.getPerformerFull(performerFullRequest) >> performerFullResponse
		0 * _
		performerFullResponse == performerFullResponseOutput
	}

}
