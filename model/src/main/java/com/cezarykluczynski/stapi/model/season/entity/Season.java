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
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"series", "episodes", "videoReleases"})
@EqualsAndHashCode(callSuper = true, exclude = {"series", "episodes", "videoReleases"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = SeasonRepository.class, singularName = "season", pluralName = "seasons")
public class Season extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_sequence_generator")
	@SequenceGenerator(name = "season_sequence_generator", sequenceName = "season_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "series_id")
	private Series series;

	private Integer seasonNumber;

	private Integer numberOfEpisodes;

	@OneToMany(mappedBy = "season", fetch = FetchType.LAZY, targetEntity = Episode.class)
	private Set<Episode> episodes = Sets.newHashSet();

	@ManyToMany(mappedBy = "seasons")
	private Set<VideoRelease> videoReleases = Sets.newHashSet();

}
