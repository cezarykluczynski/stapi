package com.cezarykluczynski.stapi.etl.common.service.step.diff;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StepDiffFormatter {

	private static final String SPACE = " ";
	private static final String EMPTY_STRING = "";

	@SuppressFBWarnings({"VA_FORMAT_STRING_USES_NEWLINE"})
	public String format(StepDiff stepDiff) {
		final String stepName = stepDiff.getStepName();
		final List<String> uniquePreviousNames = Lists.newArrayList(stepDiff.getUniquePreviousNames());
		final List<String> uniqueCurrentNames = Lists.newArrayList(stepDiff.getUniqueCurrentNames());
		if (uniquePreviousNames.isEmpty() && uniqueCurrentNames.isEmpty()) {
			return String.format("No differences found for step %s.", stepName);
		}
		uniquePreviousNames.add(0, "Previous version unique values:");
		uniqueCurrentNames.add(0, "Current version unique values:");
		int maxLeftSideLength = uniquePreviousNames.stream().mapToInt(s -> s.length()).max().getAsInt();
		int maxHeight = Ints.max(uniqueCurrentNames.size(), uniqueCurrentNames.size());
		List<String> lines = Lists.newArrayList(String.format("Differences for step %s", stepName));
		for (int i = 0; i < maxHeight; i++) {
			String leftSizeNotPadded = uniquePreviousNames.size() > i ? uniquePreviousNames.get(i) : EMPTY_STRING;
			String leftSide = StringUtils.rightPad(leftSizeNotPadded, maxLeftSideLength, SPACE);
			String rightSide = uniqueCurrentNames.size() > i ? uniqueCurrentNames.get(i) : EMPTY_STRING;
			lines.add(String.format("%s | %s", leftSide, rightSide));
		}
		return String.format("\n%s", lines.stream().collect(Collectors.joining("\n")));
	}

}
