package com.cezarykluczynski.stapi.server.common.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

public class RestEndpointDetailsDTO {

	private List<RestEndpointDetailDTO> details;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public RestEndpointDetailsDTO(List<RestEndpointDetailDTO> details) {
		this.details = details;
	}

	@SuppressFBWarnings("EI_EXPOSE_REP")
	public List<RestEndpointDetailDTO> getDetails() {
		return details;
	}
}
