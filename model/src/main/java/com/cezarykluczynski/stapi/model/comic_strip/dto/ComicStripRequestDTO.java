package com.cezarykluczynski.stapi.model.comic_strip.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ComicStripRequestDTO {

	private String uid;

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfPagesFrom;

	private Integer numberOfPagesTo;

	private Integer yearFrom;

	private Integer yearTo;

	private RequestSortDTO sort;

}
