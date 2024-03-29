package com.cezarykluczynski.stapi.model.book.entity;

import com.cezarykluczynski.stapi.model.book.repository.BookRepository;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
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
@ToString(callSuper = true, exclude = {"bookSeries", "authors", "artists", "editors", "audiobookNarrators", "publishers", "audiobookPublishers",
		"characters", "references", "audiobookReferences", "bookCollections"})
@EqualsAndHashCode(callSuper = true, exclude = {"bookSeries", "authors", "artists", "editors", "audiobookNarrators", "publishers",
		"audiobookPublishers", "characters", "references", "audiobookReferences", "bookCollections"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = BookRepository.class, singularName = "book", pluralName = "books",
		restApiVersion = "v2")
public class Book extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence_generator")
	@SequenceGenerator(name = "book_sequence_generator", sequenceName = "book_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private Integer publishedYear;

	private Integer publishedMonth;

	private Integer publishedDay;

	private Integer numberOfPages;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean novel;

	private Boolean referenceBook;

	private Boolean biographyBook;

	private Boolean rolePlayingBook;

	@SuppressWarnings("MemberName")
	private Boolean eBook;

	private Boolean anthology;

	private Boolean novelization;

	private Boolean unauthorizedPublication;

	private Boolean audiobook;

	private Boolean audiobookAbridged;

	private Integer audiobookPublishedYear;

	private Integer audiobookPublishedMonth;

	private Integer audiobookPublishedDay;

	private Integer audiobookRunTime;

	private String productionNumber;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "books_book_series",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "book_series_id", nullable = false, updatable = false))
	private Set<BookSeries> bookSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "books_authors",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> authors = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "books_artists",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> artists = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "books_editors",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> editors = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "audiobooks_narrators",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> audiobookNarrators = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "books_publishers",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	private Set<Company> publishers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "audiobooks_publishers",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	private Set<Company> audiobookPublishers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "books_characters",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	private Set<Character> characters = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "books_references",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "reference_id", nullable = false, updatable = false))
	private Set<Reference> references = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "audiobooks_references",
			joinColumns = @JoinColumn(name = "book_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "reference_id", nullable = false, updatable = false))
	private Set<Reference> audiobookReferences = Sets.newHashSet();

	@ManyToMany(mappedBy = "books")
	private Set<BookCollection> bookCollections = Sets.newHashSet();

}
