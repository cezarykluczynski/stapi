package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

import java.util.List;

@Data
public class EndpointDocumentationDTO {

	List<EndpointMethodDTO> endpointMethods;

}
