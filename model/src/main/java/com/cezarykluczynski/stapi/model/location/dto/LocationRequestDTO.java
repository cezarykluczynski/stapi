package com.cezarykluczynski.stapi.model.location.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LocationRequestDTO {

	private String uid;

	private String name;

	private Boolean earthlyLocation;

	private Boolean fictionalLocation;

	private Boolean religiousLocation;

	private Boolean geographicalLocation;

	private Boolean bodyOfWater;

	private Boolean country;

	private Boolean subnationalEntity;

	private Boolean settlement;

	private Boolean usSettlement;

	private Boolean bajoranSettlement;

	private Boolean colony;

	private Boolean landform;

	private Boolean landmark;

	private Boolean road;

	private Boolean structure;

	private Boolean shipyard;

	private Boolean buildingInterior;

	private Boolean establishment;

	private Boolean medicalEstablishment;

	private Boolean ds9Establishment;

	private Boolean school;

	private Boolean mirror;

	private Boolean alternateReality;

	private RequestSortDTO sort;

}
