package com.cezarykluczynski.stapi.model.technology.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = TechnologyRepository.class, singularName = "technology",
		pluralName = "technology")
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

	private Boolean tool;

	private Boolean culinaryTool;

	private Boolean engineeringTool;

	private Boolean householdTool;

	private Boolean medicalEquipment;

	private Boolean transporterTechnology;

}
