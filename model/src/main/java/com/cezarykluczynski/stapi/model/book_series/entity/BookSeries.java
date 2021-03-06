package com.cezarykluczynski.stapi.model.book_series.entity;


import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
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
@ToString(callSuper = true, exclude = {"parentSeries", "childSeries", "publishers", "books"})
@EqualsAndHashCode(callSuper = true, exclude = {"parentSeries", "childSeries", "publishers", "books"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
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
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<BookSeries> parentSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "book_series_book_series",
			joinColumns = @JoinColumn(name = "book_series_parent_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "book_series_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<BookSeries> childSeries = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "book_series_publishers",
			joinColumns = @JoinColumn(name = "book_series_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Company> publishers = Sets.newHashSet();

	@ManyToMany(mappedBy = "bookSeries")
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Book> books;

}
