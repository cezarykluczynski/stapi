package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.TechnologyApi
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFullResponse
import com.cezarykluczynski.stapi.util.AbstractTechnologyTest

class TechnologyTest extends AbstractTechnologyTest {

	private TechnologyApi technologyApiMock

	private Technology technology

	void setup() {
		technologyApiMock = Mock()
		technology = new Technology(technologyApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		TechnologyFullResponse technologyFullResponse = Mock()

		when:
		TechnologyFullResponse technologyFullResponseOutput = technology.get(UID)

		then:
		1 * technologyApiMock.technologyGet(UID, API_KEY) >> technologyFullResponse
		0 * _
		technologyFullResponse == technologyFullResponseOutput
	}

	void "searches entities"() {
		given:
		TechnologyBaseResponse technologyBaseResponse = Mock()

		when:
		TechnologyBaseResponse technologyBaseResponseOutput = technology.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, BORG_TECHNOLOGY, BORG_COMPONENT,
				COMMUNICATIONS_TECHNOLOGY, COMPUTER_TECHNOLOGY, COMPUTER_PROGRAMMING, SUBROUTINE, DATABASE, ENERGY_TECHNOLOGY, FICTIONAL_TECHNOLOGY,
				HOLOGRAPHIC_TECHNOLOGY, IDENTIFICATION_TECHNOLOGY, LIFE_SUPPORT_TECHNOLOGY, SENSOR_TECHNOLOGY, SHIELD_TECHNOLOGY, TOOL, CULINARY_TOOL,
				ENGINEERING_TOOL, HOUSEHOLD_TOOL, MEDICAL_EQUIPMENT, TRANSPORTER_TECHNOLOGY)

		then:
		1 * technologyApiMock.technologySearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, BORG_TECHNOLOGY, BORG_COMPONENT,
				COMMUNICATIONS_TECHNOLOGY, COMPUTER_TECHNOLOGY, COMPUTER_PROGRAMMING, SUBROUTINE, DATABASE, ENERGY_TECHNOLOGY, FICTIONAL_TECHNOLOGY,
				HOLOGRAPHIC_TECHNOLOGY, IDENTIFICATION_TECHNOLOGY, LIFE_SUPPORT_TECHNOLOGY, SENSOR_TECHNOLOGY, SHIELD_TECHNOLOGY, TOOL, CULINARY_TOOL,
				ENGINEERING_TOOL, HOUSEHOLD_TOOL, MEDICAL_EQUIPMENT, TRANSPORTER_TECHNOLOGY) >> technologyBaseResponse
		0 * _
		technologyBaseResponse == technologyBaseResponseOutput
	}

}
