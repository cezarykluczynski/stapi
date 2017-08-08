package com.cezarykluczynski.stapi.model.video_game.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class VideoGameRequestDTO {

	private String uid;

	private String title;

	private LocalDate releaseDateFrom;

	private LocalDate releaseDateTo;

	private RequestSortDTO sort;

}
