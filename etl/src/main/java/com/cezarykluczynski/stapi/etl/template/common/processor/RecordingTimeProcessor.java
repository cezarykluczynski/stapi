package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RecordingTimeProcessor implements ItemProcessor<String, Integer> {

	private static final String COLON = ":";

	@Override
	public Integer process(String item) throws Exception {
		if (!StringUtils.contains(item, COLON)) {
			return null;
		}

		List<String> parts = Lists.newArrayList(StringUtils.split(item, COLON));

		final int partsSize = parts.size();

		String minutesCandiate;
		String hoursCandidate;


		Integer seconds = Ints.tryParse(parts.get(partsSize - 1));

		if (seconds == null) {
			return null;
		}

		if (parts.size() > 1) {
			minutesCandiate = parts.get(partsSize - 2);
			Integer minutes = Ints.tryParse(minutesCandiate);
			if (minutes == null) {
				return null;
			}

			seconds += minutes * 60;
		}

		if (parts.size() > 2) {
			hoursCandidate = parts.get(partsSize - 3);
			Integer hours = Ints.tryParse(hoursCandidate);
			if (hours == null) {
				return null;
			}

			seconds += hours * 3600;
		}

		return seconds;
	}

}
