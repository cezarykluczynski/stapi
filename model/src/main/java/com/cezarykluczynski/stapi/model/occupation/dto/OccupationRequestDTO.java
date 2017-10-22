package com.cezarykluczynski.stapi.model.occupation.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OccupationRequestDTO {

	private String uid;

	private String name;

	private Boolean legalOccupation;

	private Boolean medicalOccupation;

	private Boolean scientificOccupation;

	private RequestSortDTO sort;

}
