package com.cezarykluczynski.stapi.server.technology.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFull
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import org.mapstruct.factory.Mappers

class TechnologyFullSoapMapperTest extends AbstractTechnologyMapperTest {

	private TechnologyFullSoapMapper technologyFullSoapMapper

	void setup() {
		technologyFullSoapMapper = Mappers.getMapper(TechnologyFullSoapMapper)
	}

	void "maps SOAP TechnologyFullRequest to TechnologyBaseRequestDTO"() {
		given:
		TechnologyFullRequest technologyFullRequest = new TechnologyFullRequest(uid: UID)

		when:
		TechnologyRequestDTO technologyRequestDTO = technologyFullSoapMapper.mapFull technologyFullRequest

		then:
		technologyRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Technology technology = createTechnology()

		when:
		TechnologyFull technologyFull = technologyFullSoapMapper.mapFull(technology)

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
