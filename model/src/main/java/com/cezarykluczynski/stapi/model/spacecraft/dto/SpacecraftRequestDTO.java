package com.cezarykluczynski.stapi.model.spacecraft.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SpacecraftRequestDTO {

	private String uid;

	private String name;

	private RequestSortDTO sort;
}
