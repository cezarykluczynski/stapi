package com.cezarykluczynski.stapi.model.astronomical_object.dto;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType;
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
