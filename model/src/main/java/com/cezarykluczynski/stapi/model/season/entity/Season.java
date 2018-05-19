package com.cezarykluczynski.stapi.model.season.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"series", "episodes", "videoReleases"})
@EqualsAndHashCode(callSuper = true, exclude = {"series", "episodes", "videoReleases"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = SeasonRepository.class, singularName = "season", pluralName = "seasons")
public class Season extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_sequence_generator")
	@SequenceGenerator(name = "season_sequence_generator", sequenceName = "season_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "series_id")
	private Series series;

	private Integer seasonNumber;

	private Integer numberOfEpisodes;

	@OneToMany(mappedBy = "season", fetch = FetchType.LAZY, targetEntity = Episode.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Episode> episodes = Sets.newHashSet();

	@OneToMany(mappedBy = "season", fetch = FetchType.LAZY, targetEntity = VideoRelease.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<VideoRelease> videoReleases = Sets.newHashSet();

}
