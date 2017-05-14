package com.cezarykluczynski.stapi.contract.documentation.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class EntityDTO {

	private String name;

	private String description;

	private List<EntityDTO> fields = Lists.newArrayList();

	private boolean scalarType;

	private boolean oneToManyRelation;

	private boolean manyToManyRelation;

}
