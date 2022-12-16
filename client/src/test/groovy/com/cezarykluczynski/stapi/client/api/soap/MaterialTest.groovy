package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.MaterialPortType
import spock.lang.Specification

class MaterialTest extends Specification {

	private MaterialPortType materialPortTypeMock

	private Material material

	void setup() {
		materialPortTypeMock = Mock()
		material = new Material(materialPortTypeMock)
	}

	void "gets single entity"() {
		given:
		MaterialBaseRequest materialBaseRequest = Mock()
		MaterialBaseResponse materialBaseResponse = Mock()

		when:
		MaterialBaseResponse materialBaseResponseOutput = material.search(materialBaseRequest)

		then:
		1 * materialPortTypeMock.getMaterialBase(materialBaseRequest) >> materialBaseResponse
		0 * _
		materialBaseResponse == materialBaseResponseOutput
	}

	void "searches entities"() {
		given:
		MaterialFullRequest materialFullRequest = Mock()
		MaterialFullResponse materialFullResponse = Mock()

		when:
		MaterialFullResponse materialFullResponseOutput = material.get(materialFullRequest)

		then:
		1 * materialPortTypeMock.getMaterialFull(materialFullRequest) >> materialFullResponse
		0 * _
		materialFullResponse == materialFullResponseOutput
	}

}
