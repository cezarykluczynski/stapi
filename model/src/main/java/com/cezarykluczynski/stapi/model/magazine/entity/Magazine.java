package com.cezarykluczynski.stapi.model.magazine.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.magazine.repository.MagazineRepository;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
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

import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"magazineSeries", "editors", "publishers"})
@EqualsAndHashCode(callSuper = true, exclude = {"magazineSeries", "editors", "publishers"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = MagazineRepository.class, singularName = "magazine",
		pluralName = "magazines")
public class Magazine extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "magazine_sequence_generator")
	@SequenceGenerator(name = "magazine_sequence_generator", sequenceName = "magazine_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private Integer publishedYear;

	private Integer publishedMonth;

	private Integer publishedDay;

	private Integer coverYear;

	private Integer coverMonth;

	private Integer coverDay;

	private Integer numberOfPages;

	private String issueNumber;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "magazines_magazine_series",
			joinColumns = @JoinColumn(name = "magazine_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "magazine_series_id", nullable = false, updatable = false))
	private Set<MagazineSeries> magazineSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "magazines_editors",
			joinColumns = @JoinColumn(name = "magazine_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> editors = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "magazines_publishers",
			joinColumns = @JoinColumn(name = "magazine_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	private Set<Company> publishers = Sets.newHashSet();

}
