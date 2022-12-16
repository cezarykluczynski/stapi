package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.TechnologyApi
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractTechnologyTest

class TechnologyTest extends AbstractTechnologyTest {

	private TechnologyApi technologyApiMock

	private Technology technology

	void setup() {
		technologyApiMock = Mock()
		technology = new Technology(technologyApiMock)
	}

	void "gets single entity"() {
		given:
		TechnologyFullResponse technologyFullResponse = Mock()

		when:
		TechnologyFullResponse technologyFullResponseOutput = technology.get(UID)

		then:
		1 * technologyApiMock.v1RestTechnologyGet(UID, null) >> technologyFullResponse
		0 * _
		technologyFullResponse == technologyFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		TechnologyV2FullResponse technologyV2FullResponse = Mock()

		when:
		TechnologyV2FullResponse technologyV2FullResponseOutput = technology.getV2(UID)

		then:
		1 * technologyApiMock.v2RestTechnologyGet(UID) >> technologyV2FullResponse
		0 * _
		technologyV2FullResponse == technologyV2FullResponseOutput
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
		1 * technologyApiMock.v1RestTechnologySearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, NAME, BORG_TECHNOLOGY, BORG_COMPONENT,
				COMMUNICATIONS_TECHNOLOGY, COMPUTER_TECHNOLOGY, COMPUTER_PROGRAMMING, SUBROUTINE, DATABASE, ENERGY_TECHNOLOGY, FICTIONAL_TECHNOLOGY,
				HOLOGRAPHIC_TECHNOLOGY, IDENTIFICATION_TECHNOLOGY, LIFE_SUPPORT_TECHNOLOGY, SENSOR_TECHNOLOGY, SHIELD_TECHNOLOGY, TOOL, CULINARY_TOOL,
				ENGINEERING_TOOL, HOUSEHOLD_TOOL, MEDICAL_EQUIPMENT, TRANSPORTER_TECHNOLOGY) >> technologyBaseResponse
		0 * _
		technologyBaseResponse == technologyBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		TechnologyV2BaseResponse technologyV2BaseResponse = Mock()

		when:
		TechnologyV2BaseResponse technologyV2BaseResponseOutput = technology.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, BORG_TECHNOLOGY,
				BORG_COMPONENT, COMMUNICATIONS_TECHNOLOGY, COMPUTER_TECHNOLOGY, COMPUTER_PROGRAMMING, SUBROUTINE, DATABASE, ENERGY_TECHNOLOGY,
				FICTIONAL_TECHNOLOGY, HOLOGRAPHIC_TECHNOLOGY, IDENTIFICATION_TECHNOLOGY, LIFE_SUPPORT_TECHNOLOGY, SENSOR_TECHNOLOGY,
				SHIELD_TECHNOLOGY, SECURITY_TECHNOLOGY, PROPULSION_TECHNOLOGY, SPACECRAFT_COMPONENT, WARP_TECHNOLOGY, TRANSWARP_TECHNOLOGY,
				TIME_TRAVEL_TECHNOLOGY, MILITARY_TECHNOLOGY, VICTUAL_TECHNOLOGY, TOOL, CULINARY_TOOL, ENGINEERING_TOOL, HOUSEHOLD_TOOL,
				MEDICAL_EQUIPMENT, TRANSPORTER_TECHNOLOGY, TRANSPORTATION_TECHNOLOGY, WEAPON_COMPONENT, ARTIFICIAL_LIFEFORM_COMPONENT)

		then:
		1 * technologyApiMock.v2RestTechnologySearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, BORG_TECHNOLOGY, BORG_COMPONENT,
				COMMUNICATIONS_TECHNOLOGY, COMPUTER_TECHNOLOGY, COMPUTER_PROGRAMMING, SUBROUTINE, DATABASE, ENERGY_TECHNOLOGY, FICTIONAL_TECHNOLOGY,
				HOLOGRAPHIC_TECHNOLOGY, IDENTIFICATION_TECHNOLOGY, LIFE_SUPPORT_TECHNOLOGY, SENSOR_TECHNOLOGY, SHIELD_TECHNOLOGY,
				SECURITY_TECHNOLOGY, PROPULSION_TECHNOLOGY, SPACECRAFT_COMPONENT, WARP_TECHNOLOGY, TRANSWARP_TECHNOLOGY, TIME_TRAVEL_TECHNOLOGY,
				MILITARY_TECHNOLOGY, VICTUAL_TECHNOLOGY, TOOL, CULINARY_TOOL, ENGINEERING_TOOL, HOUSEHOLD_TOOL, MEDICAL_EQUIPMENT,
				TRANSPORTER_TECHNOLOGY, TRANSPORTATION_TECHNOLOGY, WEAPON_COMPONENT, ARTIFICIAL_LIFEFORM_COMPONENT) >> technologyV2BaseResponse
		0 * _
		technologyV2BaseResponse == technologyV2BaseResponseOutput
	}

}
