package com.cezarykluczynski.stapi.model.technology.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TechnologyRequestDTO {

	private String uid;

	private String name;

	private Boolean borgTechnology;

	private Boolean borgComponent;

	private Boolean communicationsTechnology;

	private Boolean computerTechnology;

	private Boolean computerProgramming;

	private Boolean subroutine;

	private Boolean database;

	private Boolean energyTechnology;

	private Boolean fictionalTechnology;

	private Boolean holographicTechnology;

	private Boolean identificationTechnology;

	private Boolean lifeSupportTechnology;

	private Boolean sensorTechnology;

	private Boolean shieldTechnology;

	private Boolean securityTechnology;

	private Boolean propulsionTechnology;

	private Boolean spacecraftComponent;

	private Boolean warpTechnology;

	private Boolean transwarpTechnology;

	private Boolean timeTravelTechnology;

	private Boolean militaryTechnology;

	private Boolean victualTechnology;

	private Boolean tool;

	private Boolean culinaryTool;

	private Boolean engineeringTool;

	private Boolean householdTool;

	private Boolean medicalEquipment;

	private Boolean transporterTechnology;

	private Boolean transportationTechnology;

	private Boolean weaponComponent;

	private Boolean artificialLifeformComponent;

	private RequestSortDTO sort;

}
