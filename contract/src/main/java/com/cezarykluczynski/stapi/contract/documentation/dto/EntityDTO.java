package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

import java.util.List;

@Data
public class EntityDTO {

	private String name;

	private List<EntityDTO> fields;

	private boolean scalarType;

	private boolean oneToManyRelation;

	private boolean manyToManyRelation;

}
