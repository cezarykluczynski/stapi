package com.cezarykluczynski.stapi.etl.common.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

@Service
public class PeriodCandidateDetector {

	public boolean isPeriodCandidate(String item) {
		return StringUtils.length(item) > 2 && NumberUtils.isDigits(StringUtils.substring(item, 0, 2));
	}

}
