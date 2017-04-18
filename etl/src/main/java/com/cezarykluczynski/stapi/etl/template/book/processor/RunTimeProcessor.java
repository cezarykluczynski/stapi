package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import liquibase.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
class RunTimeProcessor implements ItemProcessor<String, Integer> {

	private static final Integer MINUTES_IN_HOUR = 60;
	private static final Pattern HOURS = Pattern.compile(".*?(\\d{1,2})(\\shour).*", Pattern.CASE_INSENSITIVE);
	private static final Pattern MINUTES = Pattern.compile(".*?(\\d{1,3})(\\sminute).*", Pattern.CASE_INSENSITIVE);

	@Override
	public Integer process(String item) throws Exception {
		if (StringUtils.isEmpty(item)) {
			return null;
		}

		List<String> parts = toParts(item);
		Integer length = toLength(parts);

		if (length > 0) {
			return length;
		}

		log.warn("Value {} could not be parsed into run time", item);
		return length > 0 ? length : null;
	}

	private List<String> toParts(String item) {
		return Lists.newArrayList(item.split("<br\\s?/?>"));
	}

	private Integer toLength(List<String> parts) {
		return parts.stream()
				.mapToInt(this::tryParse)
				.max().orElse(0);
	}

	private int tryParse(String runTimeCandidate) {
		return tryParseMinutes(runTimeCandidate) + tryParseHoursIntoMinutes(runTimeCandidate);
	}

	private Integer tryParseMinutes(String runTimeCandidate) {
		Integer minutes = null;

		Matcher minutesMatcher = MINUTES.matcher(runTimeCandidate);
		if (minutesMatcher.matches()) {
			minutes = Ints.tryParse(minutesMatcher.group(1));
		}

		return minutes == null ? 0 : minutes;
	}

	private Integer tryParseHoursIntoMinutes(String runTimeCandidate) {
		Integer hours = null;

		Matcher hoursMatcher = HOURS.matcher(runTimeCandidate);
		if (hoursMatcher.matches()) {
			hours = Ints.tryParse(hoursMatcher.group(1));
		}

		return hours == null ? 0 : hours * MINUTES_IN_HOUR;
	}

}
