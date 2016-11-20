package com.cezarykluczynski.stapi.model.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestOrderDTO {

	List<RequestOrderClauseDTO> clauses;

}
