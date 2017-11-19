package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.SpacecraftClassApi
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFullResponse
import com.cezarykluczynski.stapi.util.AbstractSpacecraftClassTest

class SpacecraftClassTest extends AbstractSpacecraftClassTest {

	private SpacecraftClassApi spacecraftClassApiMock

	private SpacecraftClass spacecraftClass

	void setup() {
		spacecraftClassApiMock = Mock()
		spacecraftClass = new SpacecraftClass(spacecraftClassApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		SpacecraftClassFullResponse spacecraftClassFullResponse = Mock()

		when:
		SpacecraftClassFullResponse spacecraftClassFullResponseOutput = spacecraftClass.get(UID)

		then:
		1 * spacecraftClassApiMock.spacecraftClassGet(UID, API_KEY) >> spacecraftClassFullResponse
		0 * _
		spacecraftClassFullResponse == spacecraftClassFullResponseOutput
	}

	void "searches entities"() {
		given:
		SpacecraftClassBaseResponse spacecraftClassBaseResponse = Mock()

		when:
		SpacecraftClassBaseResponse spacecraftClassBaseResponseOutput = spacecraftClass.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, WARP_CAPABLE,
				ALTERNATE_REALITY)

		then:
		1 * spacecraftClassApiMock.spacecraftClassSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, WARP_CAPABLE, ALTERNATE_REALITY) >>
				spacecraftClassBaseResponse
		0 * _
		spacecraftClassBaseResponse == spacecraftClassBaseResponseOutput
	}

}
