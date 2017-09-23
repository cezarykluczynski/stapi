package com.cezarykluczynski.stapi.model.title.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TitleRequestDTO {

	private String uid;

	private String name;

	private Boolean militaryRank;

	private Boolean fleetRank;

	private Boolean religiousTitle;

	private Boolean position;

	private Boolean mirror;

	private RequestSortDTO sort;

}
