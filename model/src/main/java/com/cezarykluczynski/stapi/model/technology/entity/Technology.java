package com.cezarykluczynski.stapi.model.technology.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = TechnologyRepository.class, singularName = "technology",
		pluralName = "technology pieces", restApiVersion = "v2")
public class Technology extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "technology_sequence_generator")
	@SequenceGenerator(name = "technology_sequence_generator", sequenceName = "technology_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
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

}
