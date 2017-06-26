package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SeasonNumberProcessor implements ItemProcessor<String, Integer> {

	@Override
	public Integer process(String item) throws Exception {
		String seasonNumberCandidate = StringUtils.substringAfter(item, "Season ");
		Integer seasonNumber = Ints.tryParse(seasonNumberCandidate);

		if (seasonNumber == null) {
			throw new StapiRuntimeException(String.format("Could not get series number from page title %s", item));
		}

		return seasonNumber;
	}

}
