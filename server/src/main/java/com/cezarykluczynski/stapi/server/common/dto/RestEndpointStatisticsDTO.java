package com.cezarykluczynski.stapi.server.common.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

public class RestEndpointStatisticsDTO {

	private final List<RestEndpointStatisticDTO> statistics;

	private final Long totalCount;

	private final Long relationsCount;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public RestEndpointStatisticsDTO(List<RestEndpointStatisticDTO> statistics, Long totalCount, Long relationsCount) {
		this.statistics = statistics;
		this.totalCount = totalCount;
		this.relationsCount = relationsCount;
	}

	@SuppressFBWarnings("EI_EXPOSE_REP")
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
