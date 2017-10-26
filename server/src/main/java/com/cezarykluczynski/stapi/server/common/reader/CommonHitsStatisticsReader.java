package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import com.cezarykluczynski.stapi.server.common.metrics.service.EndpointHitsReader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonHitsStatisticsReader {

	private final EndpointHitsReader endpointHitsReader;

	public CommonHitsStatisticsReader(EndpointHitsReader endpointHitsReader) {
		this.endpointHitsReader = endpointHitsReader;
	}

	public RestEndpointStatisticsDTO hitsStatistics() {
		List<RestEndpointStatisticDTO> restEndpointStatisticDTOList = endpointHitsReader.readEndpointHits()
				.entrySet()
				.stream()
				.map(entry -> {
					RestEndpointStatisticDTO restEndpointStatisticDTO = new RestEndpointStatisticDTO();
					restEndpointStatisticDTO.setName(entry.getKey().getSimpleName());
					restEndpointStatisticDTO.setCount(entry.getValue());
					return restEndpointStatisticDTO;
				})
				.collect(Collectors.toList());

		return new RestEndpointStatisticsDTO(restEndpointStatisticDTOList, endpointHitsReader.readAllHitsCount());
	}

}
