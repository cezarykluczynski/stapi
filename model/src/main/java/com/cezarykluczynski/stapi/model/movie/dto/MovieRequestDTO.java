package com.cezarykluczynski.stapi.model.movie.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class MovieRequestDTO {

	private String uid;

	private String title;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private LocalDate usReleaseDateFrom;

	private LocalDate usReleaseDateTo;

	private RequestSortDTO sort;

}
