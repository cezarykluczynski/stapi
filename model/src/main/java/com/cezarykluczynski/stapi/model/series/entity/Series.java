package com.cezarykluczynski.stapi.model.series.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.google.common.collect.Sets;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"productionCompany", "originalBroadcaster", "episodes", "seasons"})
@EqualsAndHashCode(callSuper = true, exclude = {"productionCompany", "originalBroadcaster", "episodes", "seasons"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = SeriesRepository.class, singularName = "series", pluralName = "series")
public class Series extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "series_sequence_generator")
	@SequenceGenerator(name = "series_sequence_generator", sequenceName = "series_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private String abbreviation;

	private Integer productionStartYear;

	private Integer productionEndYear;

	private LocalDate originalRunStartDate;

	private LocalDate originalRunEndDate;

	private Integer seasonsCount;

	private Integer episodesCount;

	private Integer featureLengthEpisodesCount;

	private Boolean companionSeries;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "production_company_id")
	private Company productionCompany;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "original_broadcaster_id")
	private Company originalBroadcaster;

	@OneToMany(mappedBy = "series", fetch = FetchType.LAZY, targetEntity = Episode.class)
	private Set<Episode> episodes = Sets.newHashSet();

	@OneToMany(mappedBy = "series", fetch = FetchType.LAZY, targetEntity = Season.class)
	private Set<Season> seasons = Sets.newHashSet();

}
