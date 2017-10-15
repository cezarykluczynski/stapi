package com.cezarykluczynski.stapi.model.element.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ElementRequestDTO {

	private String uid;

	private String name;

	private String symbol;

	private Boolean transuranium;

	private Boolean gammaSeries;

	private Boolean hypersonicSeries;

	private Boolean megaSeries;

	private Boolean omegaSeries;

	private Boolean transonicSeries;

	private Boolean worldSeries;

	private RequestSortDTO sort;

}
