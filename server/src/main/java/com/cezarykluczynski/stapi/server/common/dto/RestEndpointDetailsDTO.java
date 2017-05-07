package com.cezarykluczynski.stapi.server.common.dto;

import java.util.List;

public class RestEndpointDetailsDTO {

	private List<RestEndpointDetailDTO> details;

	public RestEndpointDetailsDTO(List<RestEndpointDetailDTO> details) {
		this.details = details;
	}

	public List<RestEndpointDetailDTO> getDetails() {
		return details;
	}
}
