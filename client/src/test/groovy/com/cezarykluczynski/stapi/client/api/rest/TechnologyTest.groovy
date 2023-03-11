package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.TechnologyV2SearchCriteria
import com.cezarykluczynski.stapi.client.rest.api.TechnologyApi
import com.cezarykluczynski.stapi.client.rest.model.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TechnologyFullResponse
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2FullResponse
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
		1 * technologyApiMock.v1RestTechnologyGet(UID) >> technologyFullResponse
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
		TechnologyBaseResponse technologyBaseResponseOutput = technology.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BORG_TECHNOLOGY,
				BORG_COMPONENT, COMMUNICATIONS_TECHNOLOGY, COMPUTER_TECHNOLOGY, COMPUTER_PROGRAMMING, SUBROUTINE, DATABASE, ENERGY_TECHNOLOGY,
				FICTIONAL_TECHNOLOGY, HOLOGRAPHIC_TECHNOLOGY, IDENTIFICATION_TECHNOLOGY, LIFE_SUPPORT_TECHNOLOGY, SENSOR_TECHNOLOGY,
				SHIELD_TECHNOLOGY, TOOL, CULINARY_TOOL, ENGINEERING_TOOL, HOUSEHOLD_TOOL, MEDICAL_EQUIPMENT, TRANSPORTER_TECHNOLOGY)

		then:
		1 * technologyApiMock.v1RestTechnologySearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BORG_TECHNOLOGY, BORG_COMPONENT,
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
		TechnologyV2BaseResponse technologyV2BaseResponseOutput = technology.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BORG_TECHNOLOGY,
				BORG_COMPONENT, COMMUNICATIONS_TECHNOLOGY, COMPUTER_TECHNOLOGY, COMPUTER_PROGRAMMING, SUBROUTINE, DATABASE, ENERGY_TECHNOLOGY,
				FICTIONAL_TECHNOLOGY, HOLOGRAPHIC_TECHNOLOGY, IDENTIFICATION_TECHNOLOGY, LIFE_SUPPORT_TECHNOLOGY, SENSOR_TECHNOLOGY,
				SHIELD_TECHNOLOGY, SECURITY_TECHNOLOGY, PROPULSION_TECHNOLOGY, SPACECRAFT_COMPONENT, WARP_TECHNOLOGY, TRANSWARP_TECHNOLOGY,
				TIME_TRAVEL_TECHNOLOGY, MILITARY_TECHNOLOGY, VICTUAL_TECHNOLOGY, TOOL, CULINARY_TOOL, ENGINEERING_TOOL, HOUSEHOLD_TOOL,
				MEDICAL_EQUIPMENT, TRANSPORTER_TECHNOLOGY, TRANSPORTATION_TECHNOLOGY, WEAPON_COMPONENT, ARTIFICIAL_LIFEFORM_COMPONENT)

		then:
		1 * technologyApiMock.v2RestTechnologySearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BORG_TECHNOLOGY, BORG_COMPONENT,
				COMMUNICATIONS_TECHNOLOGY, COMPUTER_TECHNOLOGY, COMPUTER_PROGRAMMING, SUBROUTINE, DATABASE, ENERGY_TECHNOLOGY, FICTIONAL_TECHNOLOGY,
				HOLOGRAPHIC_TECHNOLOGY, IDENTIFICATION_TECHNOLOGY, LIFE_SUPPORT_TECHNOLOGY, SENSOR_TECHNOLOGY, SHIELD_TECHNOLOGY,
				SECURITY_TECHNOLOGY, PROPULSION_TECHNOLOGY, SPACECRAFT_COMPONENT, WARP_TECHNOLOGY, TRANSWARP_TECHNOLOGY, TIME_TRAVEL_TECHNOLOGY,
				MILITARY_TECHNOLOGY, VICTUAL_TECHNOLOGY, TOOL, CULINARY_TOOL, ENGINEERING_TOOL, HOUSEHOLD_TOOL, MEDICAL_EQUIPMENT,
				TRANSPORTER_TECHNOLOGY, TRANSPORTATION_TECHNOLOGY, WEAPON_COMPONENT, ARTIFICIAL_LIFEFORM_COMPONENT) >> technologyV2BaseResponse
		0 * _
		technologyV2BaseResponse == technologyV2BaseResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		TechnologyV2BaseResponse technologyV2BaseResponse = Mock()
		TechnologyV2SearchCriteria technologyV2SearchCriteria = new TechnologyV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				borgTechnology: BORG_TECHNOLOGY,
				borgComponent: BORG_COMPONENT,
				communicationsTechnology: COMMUNICATIONS_TECHNOLOGY,
				computerTechnology: COMPUTER_TECHNOLOGY,
				computerProgramming: COMPUTER_PROGRAMMING,
				subroutine: SUBROUTINE,
				database: DATABASE,
				energyTechnology: ENERGY_TECHNOLOGY,
				fictionalTechnology: FICTIONAL_TECHNOLOGY,
				holographicTechnology: HOLOGRAPHIC_TECHNOLOGY,
				identificationTechnology: IDENTIFICATION_TECHNOLOGY,
				lifeSupportTechnology: LIFE_SUPPORT_TECHNOLOGY,
				sensorTechnology: SENSOR_TECHNOLOGY,
				shieldTechnology: SHIELD_TECHNOLOGY,
				securityTechnology: SECURITY_TECHNOLOGY,
				propulsionTechnology: PROPULSION_TECHNOLOGY,
				spacecraftComponent: SPACECRAFT_COMPONENT,
				warpTechnology: WARP_TECHNOLOGY,
				transwarpTechnology: TRANSWARP_TECHNOLOGY,
				timeTravelTechnology: TIME_TRAVEL_TECHNOLOGY,
				militaryTechnology: MILITARY_TECHNOLOGY,
				victualTechnology: VICTUAL_TECHNOLOGY,
				tool: TOOL,
				culinaryTool: CULINARY_TOOL,
				engineeringTool: ENGINEERING_TOOL,
				householdTool: HOUSEHOLD_TOOL,
				medicalEquipment: MEDICAL_EQUIPMENT,
				transporterTechnology: TRANSPORTER_TECHNOLOGY,
				transportationTechnology: TRANSPORTATION_TECHNOLOGY,
				weaponComponent: WEAPON_COMPONENT,
				artificialLifeformComponent: ARTIFICIAL_LIFEFORM_COMPONENT)
		technologyV2SearchCriteria.sort.addAll(SORT)

		when:
		TechnologyV2BaseResponse technologyV2BaseResponseOutput = technology.searchV2(technologyV2SearchCriteria)

		then:
		1 * technologyApiMock.v2RestTechnologySearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BORG_TECHNOLOGY, BORG_COMPONENT,
				COMMUNICATIONS_TECHNOLOGY, COMPUTER_TECHNOLOGY, COMPUTER_PROGRAMMING, SUBROUTINE, DATABASE, ENERGY_TECHNOLOGY, FICTIONAL_TECHNOLOGY,
				HOLOGRAPHIC_TECHNOLOGY, IDENTIFICATION_TECHNOLOGY, LIFE_SUPPORT_TECHNOLOGY, SENSOR_TECHNOLOGY, SHIELD_TECHNOLOGY,
				SECURITY_TECHNOLOGY, PROPULSION_TECHNOLOGY, SPACECRAFT_COMPONENT, WARP_TECHNOLOGY, TRANSWARP_TECHNOLOGY, TIME_TRAVEL_TECHNOLOGY,
				MILITARY_TECHNOLOGY, VICTUAL_TECHNOLOGY, TOOL, CULINARY_TOOL, ENGINEERING_TOOL, HOUSEHOLD_TOOL, MEDICAL_EQUIPMENT,
				TRANSPORTER_TECHNOLOGY, TRANSPORTATION_TECHNOLOGY, WEAPON_COMPONENT, ARTIFICIAL_LIFEFORM_COMPONENT) >> technologyV2BaseResponse
		0 * _
		technologyV2BaseResponse == technologyV2BaseResponseOutput
	}

}
