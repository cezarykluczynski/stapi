package com.cezarykluczynski.stapi.model.astronomicalObject.dto;

import com.cezarykluczynski.stapi.model.astronomicalObject.entity.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class AstronomicalObjectRequestDTO {

	private String uid;

	private String name;

	private AstronomicalObjectType astronomicalObjectType;

	private String locationUid;

	private RequestSortDTO sort;

}
