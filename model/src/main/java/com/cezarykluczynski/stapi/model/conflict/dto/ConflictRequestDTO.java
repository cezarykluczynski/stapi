package com.cezarykluczynski.stapi.model.conflict.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ConflictRequestDTO {

	private String uid;

	private String name;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean earthConflict;

	private Boolean federationWar;

	private Boolean klingonWar;

	private Boolean dominionWarBattle;

	private Boolean alternateReality;

	private RequestSortDTO sort;

}
