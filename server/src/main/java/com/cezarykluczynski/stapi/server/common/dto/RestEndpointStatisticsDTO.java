package com.cezarykluczynski.stapi.server.common.dto;

import java.util.List;

public class RestEndpointStatisticsDTO {

	private Long totalCount;

	private List<RestEndpointStatisticDTO> statistics;

	public RestEndpointStatisticsDTO(List<RestEndpointStatisticDTO> statistics, Long totalCount) {
		this.statistics = statistics;
		this.totalCount = totalCount;
	}

	public List<RestEndpointStatisticDTO> getStatistics() {
		return statistics;
	}

	public Long getTotalCount() {
		return totalCount;
	}

}
