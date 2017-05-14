package com.cezarykluczynski.stapi.contract.documentation.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class EndpointDocumentationDTO {

	private List<EndpointMethodDTO> endpointMethods = Lists.newArrayList();

}
