package com.cezarykluczynski.stapi.client.api.dto;

import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortOrder;
import lombok.Data;

@Data
public class RestSortClause {

	private String name;

	private RestSortOrder sortOrder;

}
