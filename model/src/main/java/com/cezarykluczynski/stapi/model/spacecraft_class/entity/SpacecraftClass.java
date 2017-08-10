package com.cezarykluczynski.stapi.model.spacecraft_class.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"affiliatedSpecies", "affiliatedOrganization", "spacecraftType"})
@EqualsAndHashCode(callSuper = true, exclude = {"affiliatedSpecies", "affiliatedOrganization", "spacecraftType"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = LocationRepository.class, singularName = "spacecraft class",
		pluralName = "spacecraft classes")
public class SpacecraftClass extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spacecraft_class_sequence_generator")
	@SequenceGenerator(name = "spacecraft_class_sequence_generator", sequenceName = "spacecraft_class_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "affiliated_species_id")
	private Species affiliatedSpecies;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "affiliated_organization_id")
	private Organization affiliatedOrganization;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "spacecraft_type_id")
	private SpacecraftType spacecraftType;

	private Integer numberOfDecks;

	private Boolean warpCapable;

	private String activeFrom;

	private String activeTo;

}
