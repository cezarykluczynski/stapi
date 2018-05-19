package com.cezarykluczynski.stapi.model.spacecraft_class.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity

@ToString(callSuper = true, exclude = {"species", "owner", "operator", "affiliation", "spacecraftTypes", "spacecrafts"})
@EqualsAndHashCode(callSuper = true, exclude = {"species", "owner", "operator", "affiliation", "spacecraftTypes", "spacecrafts"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = SpacecraftClassRepository.class, singularName = "spacecraft class",
		pluralName = "spacecraft classes")
public class SpacecraftClass extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spacecraft_class_sequence_generator")
	@SequenceGenerator(name = "spacecraft_class_sequence_generator", sequenceName = "spacecraft_class_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Integer numberOfDecks;

	private Boolean warpCapable;

	private String activeFrom;

	private String activeTo;

	private Boolean alternateReality;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "species_id")
	private Species species;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	private Organization owner;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "operator_id")
	private Organization operator;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "affiliation_id")
	private Organization affiliation;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "spacecraft_classes_types",
			joinColumns = @JoinColumn(name = "spacecraft_class_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "spacecraft_type_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<SpacecraftType> spacecraftTypes = Sets.newHashSet();

	@OneToMany(mappedBy = "spacecraftClass", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Spacecraft> spacecrafts = Sets.newHashSet();

}
