package com.cezarykluczynski.stapi.model.season.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SeasonRequestDTO {

	private String uid;

	private String title;

	private Integer seasonNumberFrom;

	private Integer seasonNumberTo;

	private Integer numberOfEpisodesFrom;

	private Integer numberOfEpisodesTo;

	private RequestSortDTO sort;

}
