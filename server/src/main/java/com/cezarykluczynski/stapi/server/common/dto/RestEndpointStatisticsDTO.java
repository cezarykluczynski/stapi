package com.cezarykluczynski.stapi.server.common.dto;

import java.util.List;

public class RestEndpointStatisticsDTO {

	private List<RestEndpointStatisticDTO> statistics;

	public RestEndpointStatisticsDTO(List<RestEndpointStatisticDTO> statistics) {
		this.statistics = statistics;
	}

	public List<RestEndpointStatisticDTO> getStatistics() {
		return statistics;
	}

}
