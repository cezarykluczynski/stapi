package com.cezarykluczynski.stapi.server.technology.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFull
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2Full
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		Technology dBTechnology = createTechnology()

		when:
		TechnologyV2Full technologyV2Full = technologyFullRestMapper.mapV2Full(dBTechnology)

		then:
		technologyV2Full.uid == UID
		technologyV2Full.name == NAME
		technologyV2Full.borgTechnology == BORG_TECHNOLOGY
		technologyV2Full.borgComponent == BORG_COMPONENT
		technologyV2Full.communicationsTechnology == COMMUNICATIONS_TECHNOLOGY
		technologyV2Full.computerTechnology == COMPUTER_TECHNOLOGY
		technologyV2Full.computerProgramming == COMPUTER_PROGRAMMING
		technologyV2Full.subroutine == SUBROUTINE
		technologyV2Full.database == DATABASE
		technologyV2Full.energyTechnology == ENERGY_TECHNOLOGY
		technologyV2Full.fictionalTechnology == FICTIONAL_TECHNOLOGY
		technologyV2Full.holographicTechnology == HOLOGRAPHIC_TECHNOLOGY
		technologyV2Full.identificationTechnology == IDENTIFICATION_TECHNOLOGY
		technologyV2Full.lifeSupportTechnology == LIFE_SUPPORT_TECHNOLOGY
		technologyV2Full.sensorTechnology == SENSOR_TECHNOLOGY
		technologyV2Full.shieldTechnology == SHIELD_TECHNOLOGY
		technologyV2Full.securityTechnology == SECURITY_TECHNOLOGY
		technologyV2Full.propulsionTechnology == PROPULSION_TECHNOLOGY
		technologyV2Full.spacecraftComponent == SPACECRAFT_COMPONENT
		technologyV2Full.warpTechnology == WARP_TECHNOLOGY
		technologyV2Full.transwarpTechnology == TRANSWARP_TECHNOLOGY
		technologyV2Full.timeTravelTechnology == TIME_TRAVEL_TECHNOLOGY
		technologyV2Full.militaryTechnology == MILITARY_TECHNOLOGY
		technologyV2Full.victualTechnology == VICTUAL_TECHNOLOGY
		technologyV2Full.tool == TOOL
		technologyV2Full.culinaryTool == CULINARY_TOOL
		technologyV2Full.engineeringTool == ENGINEERING_TOOL
		technologyV2Full.householdTool == HOUSEHOLD_TOOL
		technologyV2Full.medicalEquipment == MEDICAL_EQUIPMENT
		technologyV2Full.transporterTechnology == TRANSPORTER_TECHNOLOGY
		technologyV2Full.transportationTechnology == TRANSPORTATION_TECHNOLOGY
		technologyV2Full.weaponComponent == WEAPON_COMPONENT
		technologyV2Full.artificialLifeformComponent == ARTIFICIAL_LIFEFORM_COMPONENT
	}

}
