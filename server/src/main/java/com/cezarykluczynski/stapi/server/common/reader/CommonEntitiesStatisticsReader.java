package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.model.common.statistics.size.EntitySizeStatisticsProvider;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
class CommonEntitiesStatisticsReader {

	private final EntitySizeStatisticsProvider entitySizeStatisticsProvider;

	@Inject
	CommonEntitiesStatisticsReader(EntitySizeStatisticsProvider entitySizeStatisticsProvider) {
		this.entitySizeStatisticsProvider = entitySizeStatisticsProvider;
	}

	RestEndpointStatisticsDTO entitiesStatistics() {
		List<RestEndpointStatisticDTO> restEndpointStatisticDTOList = entitySizeStatisticsProvider.provide()
				.entrySet()
				.stream()
				.map(entry -> {
					RestEndpointStatisticDTO restEndpointStatisticDTO = new RestEndpointStatisticDTO();
					restEndpointStatisticDTO.setName(entry.getKey().getSimpleName());
					restEndpointStatisticDTO.setCount(entry.getValue());
					return restEndpointStatisticDTO;
				})
				.collect(Collectors.toList());

		return new RestEndpointStatisticsDTO(restEndpointStatisticDTOList);
	}

}
