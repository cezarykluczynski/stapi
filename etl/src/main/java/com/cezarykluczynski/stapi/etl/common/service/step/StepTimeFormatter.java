package com.cezarykluczynski.stapi.etl.common.service.step;

import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Service
public class StepTimeFormatter {

	public String format(Map<String, Range<LocalDateTime>> stepsTotalTimes) {
		String lineBreak = "\n";
		String formattedStepsTime = lineBreak + "Steps run times:" + lineBreak;

		OptionalInt longestStepNameLengthOptional = getMaxStepNameLength(stepsTotalTimes);
		if (!longestStepNameLengthOptional.isPresent()) {
			return formattedStepsTime + lineBreak;
		}

		Map<String, String> diffs = formatDurations(stepsTotalTimes);
		int maxDiffLength = getMaxDiffLength(diffs);
		int longestStepNameLength = longestStepNameLengthOptional.getAsInt();

		StringBuilder stringBuilder = new StringBuilder(formattedStepsTime);

		for (Map.Entry<String, Range<LocalDateTime>> entry : stepsTotalTimes.entrySet()) {
			stringBuilder
					.append("Step ")
					.append(padStepName(entry.getKey(), longestStepNameLength))
					.append(" lasted for ")
					.append(StringUtils.leftPad(diffs.get(entry.getKey()), maxDiffLength))
					.append(StringUtils.SPACE)
					.append(formatRange(entry.getValue()))
					.append(lineBreak);
		}

		return stringBuilder.toString();
	}

	private int getMaxDiffLength(Map<String, String> diffs) {
		return diffs.values().stream().mapToInt(String::length).max().orElse(0);
	}

	private Map<String, String> formatDurations(Map<String, Range<LocalDateTime>> stepsTotalTimes) {
		return stepsTotalTimes.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> formatDuration(entry.getValue())));
	}

	private OptionalInt getMaxStepNameLength(Map<String, Range<LocalDateTime>> stepsTotalTimes) {
		return stepsTotalTimes.keySet()
				.stream()
				.mapToInt(String::length)
				.max();
	}

	private String padStepName(String stepName, int longestStepLength) {
		return StringUtils.rightPad(stepName, longestStepLength);
	}

	private String formatDuration(Range<LocalDateTime> localDateTimeRange) {
		Diff diff = createDiff(localDateTimeRange);
		List<String> formattedUnits = getFormattedUnits(diff);
		return joinNonEmpty(formattedUnits);
	}

	private Diff createDiff(Range<LocalDateTime> localDateTimeRange) {
		LocalDateTime localDateTimeFrom = localDateTimeRange.getFrom();
		LocalDateTime localDateTimeTo = localDateTimeRange.getTo();
		long days = ChronoUnit.DAYS.between(localDateTimeFrom, localDateTimeTo);
		long hours = ChronoUnit.HOURS.between(localDateTimeFrom.plusDays(days), localDateTimeTo);
		long minutes = ChronoUnit.MINUTES.between(localDateTimeFrom.plusDays(days).plusHours(hours), localDateTimeTo);
		long seconds = ChronoUnit.SECONDS.between(localDateTimeFrom.plusDays(days).plusHours(hours).plusMinutes(minutes), localDateTimeTo);
		return Diff.of(days, hours, minutes, seconds);
	}

	private List<String> getFormattedUnits(Diff diff) {
		String daysFormatted = padIfNotEmpty(diff.getDays(), "d", false);
		String hoursFormatted = padIfNotEmpty(diff.getHours(), "h", !daysFormatted.isEmpty());
		String minutesFormatted = padIfNotEmpty(diff.getMinutes(), "m", !hoursFormatted.isEmpty());
		String secondsFormatted = padIfNotEmpty(diff.getSeconds(), "s", !minutesFormatted.isEmpty());
		return Lists.newArrayList(daysFormatted, hoursFormatted, minutesFormatted, secondsFormatted);
	}

	private String joinNonEmpty(List<String> time) {
		return time.stream()
				.filter(StringUtils::isNotEmpty)
				.collect(Collectors.joining(" "));
	}

	private String padIfNotEmpty(long value, String unit, boolean padAnyway) {
		return value > 0 || padAnyway ? StringUtils.leftPad(String.valueOf(value), 2, "0") + StringUtils.SPACE + unit : StringUtils.EMPTY;
	}

	private String formatRange(Range<LocalDateTime> value) {
		return "(between " + formatDate(value.getFrom()) + " and " + formatDate(value.getTo()) + ")";
	}

	private String formatDate(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY hh:mm:ss"));
	}

	@AllArgsConstructor(staticName = "of")
	@Getter
	private static class Diff {

		private long days;

		private long hours;

		private long minutes;

		private long seconds;

	}

}
