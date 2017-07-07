package com.cezarykluczynski.stapi.model.video_release.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class VideoReleaseRequestDTO {

	private String uid;

	private String title;

	private Integer yearFrom;

	private Integer yearTo;

	private Integer runTimeFrom;

	private Integer runTimeTo;

	private RequestSortDTO sort;

}
