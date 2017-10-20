package com.cezarykluczynski.stapi.server.technology.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBase
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams
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

}
