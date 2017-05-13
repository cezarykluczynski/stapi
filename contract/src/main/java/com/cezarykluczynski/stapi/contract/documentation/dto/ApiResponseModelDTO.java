package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ApiResponseModelDTO {

	private Set<Class> restModels;

	private Set<Class> restResponses;

	private Set<Class> soapModels;

	private Set<Class> soapResponses;

}
