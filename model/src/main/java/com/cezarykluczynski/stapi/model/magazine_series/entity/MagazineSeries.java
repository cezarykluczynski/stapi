package com.cezarykluczynski.stapi.model.magazine_series.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
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
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"publishers", "editors", "magazines"})
@EqualsAndHashCode(callSuper = true, exclude = {"publishers", "editors", "magazines"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = MagazineSeriesRepository.class, singularName = "magazine series",
		pluralName = "magazine series")
public class MagazineSeries extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "magazine_series_sequence_generator")
	@SequenceGenerator(name = "magazine_series_sequence_generator", sequenceName = "magazine_series_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private Integer publishedYearFrom;

	private Integer publishedMonthFrom;

	private Integer publishedYearTo;

	private Integer publishedMonthTo;

	private Integer numberOfIssues;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "magazine_series_publishers",
			joinColumns = @JoinColumn(name = "magazine_series_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Company> publishers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "magazine_series_editors",
			joinColumns = @JoinColumn(name = "magazine_series_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Staff> editors = Sets.newHashSet();

	@ManyToMany(mappedBy = "magazineSeries")
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Magazine> magazines;

}
