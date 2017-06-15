package com.cezarykluczynski.stapi.model.magazine.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MagazineRequestDTO {

	private String uid;

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfPagesFrom;

	private Integer numberOfPagesTo;

	private RequestSortDTO sort;

}
