package com.cezarykluczynski.stapi.server.technology.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBase
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2Base
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyV2RestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TechnologyBaseRestMapperTest extends AbstractTechnologyMapperTest {

	private TechnologyBaseRestMapper technologyBaseRestMapper

	void setup() {
		technologyBaseRestMapper = Mappers.getMapper(TechnologyBaseRestMapper)
	}

	void "maps TechnologyRestBeanParams to TechnologyRequestDTO"() {
		given:
		TechnologyRestBeanParams technologyRestBeanParams = new TechnologyRestBeanParams(
				uid: UID,
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
				tool: TOOL,
				culinaryTool: CULINARY_TOOL,
				engineeringTool: ENGINEERING_TOOL,
				householdTool: HOUSEHOLD_TOOL,
				medicalEquipment: MEDICAL_EQUIPMENT,
				transporterTechnology: TRANSPORTER_TECHNOLOGY)

		when:
		TechnologyRequestDTO technologyRequestDTO = technologyBaseRestMapper.mapBase technologyRestBeanParams

		then:
		technologyRequestDTO.uid == UID
		technologyRequestDTO.name == NAME
		technologyRequestDTO.borgTechnology == BORG_TECHNOLOGY
		technologyRequestDTO.borgComponent == BORG_COMPONENT
		technologyRequestDTO.communicationsTechnology == COMMUNICATIONS_TECHNOLOGY
		technologyRequestDTO.computerTechnology == COMPUTER_TECHNOLOGY
		technologyRequestDTO.computerProgramming == COMPUTER_PROGRAMMING
		technologyRequestDTO.subroutine == SUBROUTINE
		technologyRequestDTO.database == DATABASE
		technologyRequestDTO.energyTechnology == ENERGY_TECHNOLOGY
		technologyRequestDTO.fictionalTechnology == FICTIONAL_TECHNOLOGY
		technologyRequestDTO.holographicTechnology == HOLOGRAPHIC_TECHNOLOGY
		technologyRequestDTO.identificationTechnology == IDENTIFICATION_TECHNOLOGY
		technologyRequestDTO.lifeSupportTechnology == LIFE_SUPPORT_TECHNOLOGY
		technologyRequestDTO.sensorTechnology == SENSOR_TECHNOLOGY
		technologyRequestDTO.shieldTechnology == SHIELD_TECHNOLOGY
		technologyRequestDTO.tool == TOOL
		technologyRequestDTO.culinaryTool == CULINARY_TOOL
		technologyRequestDTO.engineeringTool == ENGINEERING_TOOL
		technologyRequestDTO.householdTool == HOUSEHOLD_TOOL
		technologyRequestDTO.medicalEquipment == MEDICAL_EQUIPMENT
		technologyRequestDTO.transporterTechnology == TRANSPORTER_TECHNOLOGY
	}

	void "maps TechnologyV2RestBeanParams to TechnologyRequestDTO"() {
		given:
		TechnologyV2RestBeanParams technologyV2RestBeanParams = new TechnologyV2RestBeanParams(
				uid: UID,
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

		when:
		TechnologyRequestDTO technologyRequestDTO = technologyBaseRestMapper.mapV2Base technologyV2RestBeanParams

		then:
		technologyRequestDTO.uid == UID
		technologyRequestDTO.name == NAME
		technologyRequestDTO.borgTechnology == BORG_TECHNOLOGY
		technologyRequestDTO.borgComponent == BORG_COMPONENT
		technologyRequestDTO.communicationsTechnology == COMMUNICATIONS_TECHNOLOGY
		technologyRequestDTO.computerTechnology == COMPUTER_TECHNOLOGY
		technologyRequestDTO.computerProgramming == COMPUTER_PROGRAMMING
		technologyRequestDTO.subroutine == SUBROUTINE
		technologyRequestDTO.database == DATABASE
		technologyRequestDTO.energyTechnology == ENERGY_TECHNOLOGY
		technologyRequestDTO.fictionalTechnology == FICTIONAL_TECHNOLOGY
		technologyRequestDTO.holographicTechnology == HOLOGRAPHIC_TECHNOLOGY
		technologyRequestDTO.identificationTechnology == IDENTIFICATION_TECHNOLOGY
		technologyRequestDTO.lifeSupportTechnology == LIFE_SUPPORT_TECHNOLOGY
		technologyRequestDTO.sensorTechnology == SENSOR_TECHNOLOGY
		technologyRequestDTO.shieldTechnology == SHIELD_TECHNOLOGY
		technologyRequestDTO.securityTechnology == SECURITY_TECHNOLOGY
		technologyRequestDTO.propulsionTechnology == PROPULSION_TECHNOLOGY
		technologyRequestDTO.spacecraftComponent == SPACECRAFT_COMPONENT
		technologyRequestDTO.warpTechnology == WARP_TECHNOLOGY
		technologyRequestDTO.transwarpTechnology == TRANSWARP_TECHNOLOGY
		technologyRequestDTO.timeTravelTechnology == TIME_TRAVEL_TECHNOLOGY
		technologyRequestDTO.militaryTechnology == MILITARY_TECHNOLOGY
		technologyRequestDTO.victualTechnology == VICTUAL_TECHNOLOGY
		technologyRequestDTO.tool == TOOL
		technologyRequestDTO.culinaryTool == CULINARY_TOOL
		technologyRequestDTO.engineeringTool == ENGINEERING_TOOL
		technologyRequestDTO.householdTool == HOUSEHOLD_TOOL
		technologyRequestDTO.medicalEquipment == MEDICAL_EQUIPMENT
		technologyRequestDTO.transporterTechnology == TRANSPORTER_TECHNOLOGY
		technologyRequestDTO.transportationTechnology == TRANSPORTATION_TECHNOLOGY
		technologyRequestDTO.weaponComponent == WEAPON_COMPONENT
		technologyRequestDTO.artificialLifeformComponent == ARTIFICIAL_LIFEFORM_COMPONENT
	}

	void "maps DB entity to base REST entity"() {
		given:
		Technology technology = createTechnology()

		when:
		TechnologyBase technologyBase = technologyBaseRestMapper.mapBase(Lists.newArrayList(technology))[0]

		then:
		technologyBase.uid == UID
		technologyBase.name == NAME
		technologyBase.borgTechnology == BORG_TECHNOLOGY
		technologyBase.borgComponent == BORG_COMPONENT
		technologyBase.communicationsTechnology == COMMUNICATIONS_TECHNOLOGY
		technologyBase.computerTechnology == COMPUTER_TECHNOLOGY
		technologyBase.computerProgramming == COMPUTER_PROGRAMMING
		technologyBase.subroutine == SUBROUTINE
		technologyBase.database == DATABASE
		technologyBase.energyTechnology == ENERGY_TECHNOLOGY
		technologyBase.fictionalTechnology == FICTIONAL_TECHNOLOGY
		technologyBase.holographicTechnology == HOLOGRAPHIC_TECHNOLOGY
		technologyBase.identificationTechnology == IDENTIFICATION_TECHNOLOGY
		technologyBase.lifeSupportTechnology == LIFE_SUPPORT_TECHNOLOGY
		technologyBase.sensorTechnology == SENSOR_TECHNOLOGY
		technologyBase.shieldTechnology == SHIELD_TECHNOLOGY
		technologyBase.tool == TOOL
		technologyBase.culinaryTool == CULINARY_TOOL
		technologyBase.engineeringTool == ENGINEERING_TOOL
		technologyBase.householdTool == HOUSEHOLD_TOOL
		technologyBase.medicalEquipment == MEDICAL_EQUIPMENT
		technologyBase.transporterTechnology == TRANSPORTER_TECHNOLOGY
	}

	void "maps DB entity to base REST V2 entity"() {
		given:
		Technology technology = createTechnology()

		when:
		TechnologyV2Base technologyV2Base = technologyBaseRestMapper.mapV2Base(Lists.newArrayList(technology))[0]

		then:
		technologyV2Base.uid == UID
		technologyV2Base.name == NAME
		technologyV2Base.borgTechnology == BORG_TECHNOLOGY
		technologyV2Base.borgComponent == BORG_COMPONENT
		technologyV2Base.communicationsTechnology == COMMUNICATIONS_TECHNOLOGY
		technologyV2Base.computerTechnology == COMPUTER_TECHNOLOGY
		technologyV2Base.computerProgramming == COMPUTER_PROGRAMMING
		technologyV2Base.subroutine == SUBROUTINE
		technologyV2Base.database == DATABASE
		technologyV2Base.energyTechnology == ENERGY_TECHNOLOGY
		technologyV2Base.fictionalTechnology == FICTIONAL_TECHNOLOGY
		technologyV2Base.holographicTechnology == HOLOGRAPHIC_TECHNOLOGY
		technologyV2Base.identificationTechnology == IDENTIFICATION_TECHNOLOGY
		technologyV2Base.lifeSupportTechnology == LIFE_SUPPORT_TECHNOLOGY
		technologyV2Base.sensorTechnology == SENSOR_TECHNOLOGY
		technologyV2Base.shieldTechnology == SHIELD_TECHNOLOGY
		technologyV2Base.securityTechnology == SECURITY_TECHNOLOGY
		technologyV2Base.propulsionTechnology == PROPULSION_TECHNOLOGY
		technologyV2Base.spacecraftComponent == SPACECRAFT_COMPONENT
		technologyV2Base.warpTechnology == WARP_TECHNOLOGY
		technologyV2Base.transwarpTechnology == TRANSWARP_TECHNOLOGY
		technologyV2Base.timeTravelTechnology == TIME_TRAVEL_TECHNOLOGY
		technologyV2Base.militaryTechnology == MILITARY_TECHNOLOGY
		technologyV2Base.victualTechnology == VICTUAL_TECHNOLOGY
		technologyV2Base.tool == TOOL
		technologyV2Base.culinaryTool == CULINARY_TOOL
		technologyV2Base.engineeringTool == ENGINEERING_TOOL
		technologyV2Base.householdTool == HOUSEHOLD_TOOL
		technologyV2Base.medicalEquipment == MEDICAL_EQUIPMENT
		technologyV2Base.transporterTechnology == TRANSPORTER_TECHNOLOGY
		technologyV2Base.transportationTechnology == TRANSPORTATION_TECHNOLOGY
		technologyV2Base.weaponComponent == WEAPON_COMPONENT
		technologyV2Base.artificialLifeformComponent == ARTIFICIAL_LIFEFORM_COMPONENT
	}

}
