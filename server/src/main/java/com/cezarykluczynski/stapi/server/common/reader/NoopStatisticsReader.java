package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.NOT_HITS)
public class NoopStatisticsReader implements StatisticsReader {

	@Override
	public RestEndpointStatisticsDTO hitsStatistics() {
		return new RestEndpointStatisticsDTO(Lists.newArrayList(), 0L, 0L);
	}

}
