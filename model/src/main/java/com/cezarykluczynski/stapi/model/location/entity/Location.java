package com.cezarykluczynski.stapi.model.location.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
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
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = LocationRepository.class, singularName = "location", pluralName = "locations")
public class Location extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_sequence_generator")
	@SequenceGenerator(name = "location_sequence_generator", sequenceName = "location_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Boolean earthlyLocation;

	private Boolean fictionalLocation;

	private Boolean religiousLocation;

	private Boolean geographicalLocation;

	private Boolean bodyOfWater;

	private Boolean country;

	private Boolean subnationalEntity;

	private Boolean settlement;

	private Boolean usSettlement;

	private Boolean bajoranSettlement;

	private Boolean colony;

	private Boolean landform;

	private Boolean landmark;

	private Boolean road;

	private Boolean structure;

	private Boolean shipyard;

	private Boolean buildingInterior;

	private Boolean establishment;

	private Boolean medicalEstablishment;

	@Column(name = "ds9_establishment")
	private Boolean ds9Establishment;

	private Boolean school;

	private Boolean mirror;

	private Boolean alternateReality;

}
