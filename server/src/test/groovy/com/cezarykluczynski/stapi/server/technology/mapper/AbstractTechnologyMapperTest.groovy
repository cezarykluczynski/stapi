package com.cezarykluczynski.stapi.server.technology.mapper

import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.util.AbstractTechnologyTest

abstract class AbstractTechnologyMapperTest extends AbstractTechnologyTest {

	protected static Technology createTechnology() {
		new Technology(
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
	}

}
