package com.cezarykluczynski.stapi.model.comic_series.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ComicSeriesRequestDTO {

	private String uid;

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfIssuesFrom;

	private Integer numberOfIssuesTo;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean miniseries;

	private Boolean photonovelSeries;

	private RequestSortDTO sort;

}
