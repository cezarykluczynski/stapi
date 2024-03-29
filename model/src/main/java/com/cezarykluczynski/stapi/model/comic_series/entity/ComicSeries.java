package com.cezarykluczynski.stapi.model.comic_series.entity;

import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
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
@ToString(callSuper = true, exclude = {"parentSeries", "childSeries", "publishers", "comics"})
@EqualsAndHashCode(callSuper = true, exclude = {"parentSeries", "childSeries", "publishers", "comics"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = ComicSeriesRepository.class, singularName = "comic series",
		pluralName = "comic series")
public class ComicSeries extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comic_series_sequence_generator")
	@SequenceGenerator(name = "comic_series_sequence_generator", sequenceName = "comic_series_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private Integer publishedYearFrom;

	private Integer publishedMonthFrom;

	private Integer publishedDayFrom;

	private Integer publishedYearTo;

	private Integer publishedMonthTo;

	private Integer publishedDayTo;

	private Integer numberOfIssues;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean miniseries;

	private Boolean photonovelSeries;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_series_comic_series",
			joinColumns = @JoinColumn(name = "comic_series_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "comic_series_parent_id", nullable = false, updatable = false))
	private Set<ComicSeries> parentSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_series_comic_series",
			joinColumns = @JoinColumn(name = "comic_series_parent_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "comic_series_id", nullable = false, updatable = false))
	private Set<ComicSeries> childSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_series_publishers",
			joinColumns = @JoinColumn(name = "comic_series_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	private Set<Company> publishers = Sets.newHashSet();

	@ManyToMany(mappedBy = "comicSeries")
	private Set<Comics> comics;

}
