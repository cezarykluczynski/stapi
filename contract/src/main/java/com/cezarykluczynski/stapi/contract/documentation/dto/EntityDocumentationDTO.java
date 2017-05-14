package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

@Data
public class EntityDocumentationDTO {

	private EndpointDocumentationDTO restEndpointDocumentation;

	private EndpointDocumentationDTO soapEndpointDocumentation;

}
