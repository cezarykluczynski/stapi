package com.cezarykluczynski.stapi.model.common.dto;

import com.cezarykluczynski.stapi.model.common.dto.enums.RequestOrderEnumDTO;
import lombok.Data;

@Data
public class RequestOrderClauseDTO {

	private String name;

	private RequestOrderEnumDTO order;

	private Integer clauseOrder;

}
