package com.cezarykluczynski.stapi.server.common.dto;

import java.util.List;

public class RestEndpointStatisticsDTO {

	private final List<RestEndpointStatisticDTO> statistics;

	private final Long totalCount;

	private final Long relationsCount;

	public RestEndpointStatisticsDTO(List<RestEndpointStatisticDTO> statistics, Long totalCount, Long relationsCount) {
		this.statistics = statistics;
		this.totalCount = totalCount;
		this.relationsCount = relationsCount;
	}

	public List<RestEndpointStatisticDTO> getStatistics() {
		return statistics;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public Long getRelationsCount() {
		return relationsCount;
	}

}
