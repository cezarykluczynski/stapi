package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ApiEndpointModelDTO {

	private Set<Class> restEndpoints;

	private Set<Class> soapEndpoints;

}
