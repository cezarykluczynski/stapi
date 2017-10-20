package com.cezarykluczynski.stapi.server.technology.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFull
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import org.mapstruct.factory.Mappers

class TechnologyFullRestMapperTest extends AbstractTechnologyMapperTest {

	private TechnologyFullRestMapper technologyFullRestMapper

	void setup() {
		technologyFullRestMapper = Mappers.getMapper(TechnologyFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Technology dBTechnology = createTechnology()

		when:
		TechnologyFull technologyFull = technologyFullRestMapper.mapFull(dBTechnology)

		then:
		technologyFull.uid == UID
		technologyFull.name == NAME
		technologyFull.borgTechnology == BORG_TECHNOLOGY
		technologyFull.borgComponent == BORG_COMPONENT
		technologyFull.communicationsTechnology == COMMUNICATIONS_TECHNOLOGY
		technologyFull.computerTechnology == COMPUTER_TECHNOLOGY
		technologyFull.computerProgramming == COMPUTER_PROGRAMMING
		technologyFull.subroutine == SUBROUTINE
		technologyFull.database == DATABASE
		technologyFull.energyTechnology == ENERGY_TECHNOLOGY
		technologyFull.fictionalTechnology == FICTIONAL_TECHNOLOGY
		technologyFull.holographicTechnology == HOLOGRAPHIC_TECHNOLOGY
		technologyFull.identificationTechnology == IDENTIFICATION_TECHNOLOGY
		technologyFull.lifeSupportTechnology == LIFE_SUPPORT_TECHNOLOGY
		technologyFull.sensorTechnology == SENSOR_TECHNOLOGY
		technologyFull.shieldTechnology == SHIELD_TECHNOLOGY
		technologyFull.tool == TOOL
		technologyFull.culinaryTool == CULINARY_TOOL
		technologyFull.engineeringTool == ENGINEERING_TOOL
		technologyFull.householdTool == HOUSEHOLD_TOOL
		technologyFull.medicalEquipment == MEDICAL_EQUIPMENT
		technologyFull.transporterTechnology == TRANSPORTER_TECHNOLOGY
	}

}
