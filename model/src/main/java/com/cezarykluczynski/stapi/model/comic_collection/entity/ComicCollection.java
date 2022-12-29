package com.cezarykluczynski.stapi.model.comic_collection.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.comic_collection.repository.ComicCollectionRepository;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
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
@ToString(callSuper = true, exclude = {"comicSeries", "writers", "artists", "editors", "staff", "publishers", "characters", "references", "comics",
		"childComicSeries"})
@EqualsAndHashCode(callSuper = true, exclude = {"comicSeries", "writers", "artists", "editors", "staff", "publishers", "characters", "references",
		"comics", "childComicSeries"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = ComicCollectionRepository.class, singularName = "comic collection",
		pluralName = "comic collections")
public class ComicCollection extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comic_collection_sequence_generator")
	@SequenceGenerator(name = "comic_collection_sequence_generator", sequenceName = "comic_collection_sequence", allocationSize = 1)
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

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean photonovel;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_series",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "comic_series_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<ComicSeries> comicSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_child_series",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "comic_series_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<ComicSeries> childComicSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_writers",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Staff> writers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_artists",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Staff> artists = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_editors",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Staff> editors = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_staff",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Staff> staff = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_publishers",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Company> publishers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_characters",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Character> characters = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_references",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "reference_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Reference> references = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "comic_collections_comics",
			joinColumns = @JoinColumn(name = "comic_collection_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "comics_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Comics> comics = Sets.newHashSet();

}
