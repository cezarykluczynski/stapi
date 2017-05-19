package com.cezarykluczynski.stapi.model.endpointHit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor(staticName = "of")
public class MetricsEndpointKeyDTO {

	private String endpointName;

	private String methodName;

}
