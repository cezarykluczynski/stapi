package com.cezarykluczynski.stapi.model.series.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"productionCompany", "originalBroadcaster", "episodes", "seasons"})
@EqualsAndHashCode(callSuper = true, exclude = {"productionCompany", "originalBroadcaster", "episodes", "seasons"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
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

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "production_company_id")
	private Company productionCompany;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "original_broadcaster_id")
	private Company originalBroadcaster;

	@OneToMany(mappedBy = "series", fetch = FetchType.LAZY, targetEntity = Episode.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Episode> episodes;

	@OneToMany(mappedBy = "series", fetch = FetchType.LAZY, targetEntity = Season.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Season> seasons;

}
