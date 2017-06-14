package com.cezarykluczynski.stapi.etl.template.book.dto;

import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class BookTemplate {

	private Page page;

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

	private Boolean audiobook;

	private Boolean audiobookAbridged;

	private Integer audiobookPublishedYear;

	private Integer audiobookPublishedMonth;

	private Integer audiobookPublishedDay;

	private Integer audiobookRunTime;

	private String productionNumber;

	private Set<Staff> authors = Sets.newHashSet();

	private Set<Staff> artists = Sets.newHashSet();

	private Set<Staff> editors = Sets.newHashSet();

	private Set<Staff> audiobookNarrators = Sets.newHashSet();

	private Set<Company> publishers = Sets.newHashSet();

	private Set<Company> audiobookPublishers = Sets.newHashSet();

	private Set<BookSeries> bookSeries = Sets.newHashSet();

	private Set<Character> characters = Sets.newHashSet();

	private Set<Reference> references = Sets.newHashSet();

	private Set<Reference> audiobookReferences = Sets.newHashSet();

}
