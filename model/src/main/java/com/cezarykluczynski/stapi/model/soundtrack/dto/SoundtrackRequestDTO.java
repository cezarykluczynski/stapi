package com.cezarykluczynski.stapi.model.soundtrack.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class SoundtrackRequestDTO {

	private String uid;

	private String title;

	private LocalDate releaseDateFrom;

	private LocalDate releaseDateTo;

	private Integer lengthFrom;

	private Integer lengthTo;

	private RequestSortDTO sort;

}
