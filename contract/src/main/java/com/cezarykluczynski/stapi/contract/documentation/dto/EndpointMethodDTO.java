package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

import java.util.List;

@Data
public class EndpointMethodDTO {

	private String name;

	private List<EntityDTO> arguments;

	private EntityDTO returnType;

}
