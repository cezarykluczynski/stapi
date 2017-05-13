package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ApiRequestModelDTO {

	private Set<Class> restRequests;

	private Set<Class> soapRequests;

}
