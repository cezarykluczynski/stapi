package com.cezarykluczynski.stapi.model.soundtrack.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.soundtrack.repository.SoundtrackRepository;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"labels", "composers", "contributors", "orchestrators", "references"})
@EqualsAndHashCode(callSuper = true, exclude = {"labels", "composers", "contributors", "orchestrators", "references"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = SoundtrackRepository.class, singularName = "soundtrack",
		pluralName = "soundtracks")
public class Soundtrack extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "soundtrack_sequence_generator")
	@SequenceGenerator(name = "soundtrack_sequence_generator", sequenceName = "soundtrack_sequence", allocationSize = 1)
	private Long id;

	private String title;

	private LocalDate releaseDate;

	private Integer length;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "soundtracks_labels",
			joinColumns = @JoinColumn(name = "soundtrack_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	private Set<Company> labels = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "soundtracks_composers",
			joinColumns = @JoinColumn(name = "soundtrack_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> composers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "soundtracks_contributors",
			joinColumns = @JoinColumn(name = "soundtrack_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> contributors = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "soundtracks_orchestrators",
			joinColumns = @JoinColumn(name = "soundtrack_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> orchestrators = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "soundtracks_references",
			joinColumns = @JoinColumn(name = "soundtrack_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "reference_id", nullable = false, updatable = false))
	private Set<Reference> references = Sets.newHashSet();

}
