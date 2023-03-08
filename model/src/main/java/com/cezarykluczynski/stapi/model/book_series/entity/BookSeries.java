package com.cezarykluczynski.stapi.model.book_series.entity;

import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository;
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
@ToString(callSuper = true, exclude = {"parentSeries", "childSeries", "publishers", "books"})
@EqualsAndHashCode(callSuper = true, exclude = {"parentSeries", "childSeries", "publishers", "books"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = BookSeriesRepository.class, singularName = "book series",
		pluralName = "book series")
public class BookSeries extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_series_sequence_generator")
	@SequenceGenerator(name = "book_series_sequence_generator", sequenceName = "book_series_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private Integer publishedYearFrom;

	private Integer publishedMonthFrom;

	private Integer publishedYearTo;

	private Integer publishedMonthTo;

	private Integer numberOfBooks;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean miniseries;

	@SuppressWarnings("MemberName")
	private Boolean eBookSeries;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "book_series_book_series",
			joinColumns = @JoinColumn(name = "book_series_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "book_series_parent_id", nullable = false, updatable = false))
	private Set<BookSeries> parentSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "book_series_book_series",
			joinColumns = @JoinColumn(name = "book_series_parent_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "book_series_id", nullable = false, updatable = false))
	private Set<BookSeries> childSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "book_series_publishers",
			joinColumns = @JoinColumn(name = "book_series_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	private Set<Company> publishers = Sets.newHashSet();

	@ManyToMany(mappedBy = "bookSeries")
	private Set<Book> books;

}
