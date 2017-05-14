package com.cezarykluczynski.stapi.contract.documentation.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class EndpointMethodDTO {

	private String name;

	private List<EntityDTO> arguments = Lists.newArrayList();

	private EntityDTO returnType;

}
