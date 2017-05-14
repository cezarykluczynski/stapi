package com.cezarykluczynski.stapi.contract.documentation.dto;

import com.cezarykluczynski.stapi.contract.documentation.dto.enums.ApiType;
import lombok.Data;

@Data
public class ApiEntitiesDTO {

	private ApiType apiType;

	private String entityName;

	private Class baseRequest;

	private Class fullRequest;

	private Class baseEntity;

	private Class fullEntity;

	private Class baseResponse;

	private Class fullResponse;

	private Class endpoint;

}
