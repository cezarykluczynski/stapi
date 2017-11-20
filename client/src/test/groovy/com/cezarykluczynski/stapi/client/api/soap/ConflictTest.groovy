package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ConflictPortType
import spock.lang.Specification

class ConflictTest extends Specification {

	private ConflictPortType conflictPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Conflict conflict

	void setup() {
		conflictPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		conflict = new Conflict(conflictPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		ConflictBaseRequest conflictBaseRequest = Mock()
		ConflictBaseResponse conflictBaseResponse = Mock()

		when:
		ConflictBaseResponse conflictBaseResponseOutput = conflict.search(conflictBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(conflictBaseRequest)
		1 * conflictPortTypeMock.getConflictBase(conflictBaseRequest) >> conflictBaseResponse
		0 * _
		conflictBaseResponse == conflictBaseResponseOutput
	}

	void "searches entities"() {
		given:
		ConflictFullRequest conflictFullRequest = Mock()
		ConflictFullResponse conflictFullResponse = Mock()

		when:
		ConflictFullResponse conflictFullResponseOutput = conflict.get(conflictFullRequest)

		then:
		1 * apiKeySupplierMock.supply(conflictFullRequest)
		1 * conflictPortTypeMock.getConflictFull(conflictFullRequest) >> conflictFullResponse
		0 * _
		conflictFullResponse == conflictFullResponseOutput
	}

}
