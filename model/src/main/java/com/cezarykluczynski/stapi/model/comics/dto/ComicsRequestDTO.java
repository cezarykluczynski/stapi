package com.cezarykluczynski.stapi.model.comics.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ComicsRequestDTO {

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

	private Boolean photonovel;

	private Boolean adaptation;

	private RequestSortDTO sort;

}
