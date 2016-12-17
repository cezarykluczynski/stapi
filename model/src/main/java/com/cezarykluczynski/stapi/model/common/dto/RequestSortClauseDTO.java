package com.cezarykluczynski.stapi.model.common.dto;

import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO;
import lombok.Data;

@Data
public class RequestSortClauseDTO {

	private String name;

	private RequestSortDirectionDTO direction;

	private Integer clauseOrder;

}
