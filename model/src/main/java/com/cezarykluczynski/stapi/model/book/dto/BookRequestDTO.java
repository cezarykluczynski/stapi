package com.cezarykluczynski.stapi.model.book.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class BookRequestDTO {

	private String uid;

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfPagesFrom;

	private Integer numberOfPagesTo;

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

	private Integer audiobookPublishedYearFrom;

	private Integer audiobookPublishedYearTo;

	private Integer audiobookRunTimeFrom;

	private Integer audiobookRunTimeTo;

	private RequestSortDTO sort;

}
