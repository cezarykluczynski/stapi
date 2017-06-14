package com.cezarykluczynski.stapi.model.book_series.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class BookSeriesRequestDTO {

	private String uid;

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfBooksFrom;

	private Integer numberOfBooksTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean miniseries;

	@SuppressWarnings("MemberName")
	private Boolean eBookSeries;

	private RequestSortDTO sort;

}
