package com.cezarykluczynski.stapi.model.comic_strip.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comic_strip.repository.ComicStripRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
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
@ToString(callSuper = true, exclude = {"comicSeries", "writers", "artists", "characters"})
@EqualsAndHashCode(callSuper = true, exclude = {"comicSeries", "writers", "artists", "characters"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = ComicStripRepository.class, singularName = "comic strip",
		pluralName = "comic strips")
public class ComicStrip extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comic_strip_sequence_generator")
	@SequenceGenerator(name = "comic_strip_sequence_generator", sequenceName = "comic_strip_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private String periodical;

	private Integer publishedYearFrom;

	private Integer publishedMonthFrom;

	private Integer publishedDayFrom;

	private Integer publishedYearTo;

	private Integer publishedMonthTo;

	private Integer publishedDayTo;

	private Integer numberOfPages;

	private Integer yearFrom;

	private Integer yearTo;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_strips_comics_series",
			joinColumns = @JoinColumn(name = "comic_strip_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "comic_series_id", nullable = false, updatable = false))
	private Set<ComicSeries> comicSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_strips_writers",
			joinColumns = @JoinColumn(name = "comic_strip_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> writers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_strips_artists",
			joinColumns = @JoinColumn(name = "comic_strip_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> artists = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_strips_characters",
			joinColumns = @JoinColumn(name = "comic_strip_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	private Set<Character> characters = Sets.newHashSet();

}
