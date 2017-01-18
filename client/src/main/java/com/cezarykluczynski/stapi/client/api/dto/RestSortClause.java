package com.cezarykluczynski.stapi.client.api.dto;

import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection;
import lombok.Data;

@Data
public class RestSortClause {

	private String name;

	private RestSortDirection direction;

}
