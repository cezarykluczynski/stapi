package com.cezarykluczynski.stapi.model.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestSortDTO {

	private List<RequestSortClauseDTO> clauses;

}
