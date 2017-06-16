package com.cezarykluczynski.stapi.model.magazine_series.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MagazineSeriesRequestDTO {

	private String uid;

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfIssuesFrom;

	private Integer numberOfIssuesTo;

	private RequestSortDTO sort;

}
