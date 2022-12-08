package com.cezarykluczynski.stapi.model.occupation.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OccupationRequestDTO {

	private String uid;

	private String name;

	private Boolean artsOccupation;

	private Boolean communicationOccupation;

	private Boolean economicOccupation;

	private Boolean educationOccupation;

	private Boolean entertainmentOccupation;

	private Boolean illegalOccupation;

	private Boolean legalOccupation;

	private Boolean medicalOccupation;

	private Boolean scientificOccupation;

	private Boolean sportsOccupation;

	private Boolean victualOccupation;

	private RequestSortDTO sort;

}
