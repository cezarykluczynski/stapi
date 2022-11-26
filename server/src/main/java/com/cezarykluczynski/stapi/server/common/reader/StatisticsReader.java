package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;

public interface StatisticsReader {

	RestEndpointStatisticsDTO hitsStatistics();

}
