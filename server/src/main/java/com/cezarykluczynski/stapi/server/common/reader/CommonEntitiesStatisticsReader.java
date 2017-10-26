package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.model.common.statistics.size.EntitySizeStatisticsProvider;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

@Service
class CommonEntitiesStatisticsReader {

	private final EntitySizeStatisticsProvider entitySizeStatisticsProvider;

	CommonEntitiesStatisticsReader(EntitySizeStatisticsProvider entitySizeStatisticsProvider) {
		this.entitySizeStatisticsProvider = entitySizeStatisticsProvider;
	}

	RestEndpointStatisticsDTO entitiesStatistics() {
		LongAdder longAdder = new LongAdder();

		List<RestEndpointStatisticDTO> restEndpointStatisticDTOList = entitySizeStatisticsProvider.provide()
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue() > 0L)
				.map(entry -> {
					Long count = entry.getValue();
					RestEndpointStatisticDTO restEndpointStatisticDTO = new RestEndpointStatisticDTO();
					restEndpointStatisticDTO.setName(entry.getKey().getSimpleName());
					restEndpointStatisticDTO.setCount(count);
					longAdder.add(count);
					return restEndpointStatisticDTO;
				})
				.collect(Collectors.toList());

		return new RestEndpointStatisticsDTO(restEndpointStatisticDTOList, longAdder.longValue());
	}

}
