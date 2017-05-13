package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

@Data
public class EndpointsDocumentationDTO {

	private EndpointDocumentationDTO restRndpointDocumentation;

	private EndpointDocumentationDTO soapRndpointDocumentation;

}
