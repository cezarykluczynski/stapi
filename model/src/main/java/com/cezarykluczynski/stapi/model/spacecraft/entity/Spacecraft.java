package com.cezarykluczynski.stapi.model.spacecraft.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"spacecraftClass", "owner", "operator", "spacecraftTypes"})
@EqualsAndHashCode(callSuper = true, exclude = {"spacecraftClass", "owner", "operator", "spacecraftTypes"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = SpacecraftRepository.class, singularName = "spacecraft",
		pluralName = "spacecrafts")
public class Spacecraft extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spacecraft_sequence_generator")
	@SequenceGenerator(name = "spacecraft_sequence_generator", sequenceName = "spacecraft_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String registry;

	private String status;

	private String dateStatus;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "spacecraft_class_id")
	private SpacecraftClass spacecraftClass;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	private Organization owner;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "operator_id")
	private Organization operator;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "spacecrafts_spacecraft_types",
			joinColumns = @JoinColumn(name = "spacecraft_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "spacecraft_type_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<SpacecraftType> spacecraftTypes = Sets.newHashSet();

}
