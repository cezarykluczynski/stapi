package com.cezarykluczynski.stapi.model.spacecraft_class.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SpacecraftClassRequestDTO {

	private String uid;

	private String name;

	private Boolean warpCapable;

	private Boolean alternateReality;

	private RequestSortDTO sort;

}
