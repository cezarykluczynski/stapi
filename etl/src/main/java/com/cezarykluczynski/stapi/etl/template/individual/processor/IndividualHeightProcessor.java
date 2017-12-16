package com.cezarykluczynski.stapi.etl.template.individual.processor;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class IndividualHeightProcessor implements ItemProcessor<String, Integer> {

	private static final Pattern FEET_INCH = Pattern.compile("(\\d{1,2})'(\\s?)([0-9]|(1[0-1]))(\"|'')");
	private static final Pattern FEET_INCH_VERBOSE = Pattern.compile("(\\d{1,2})(\\s?)(ft\\.)");
	private static final Pattern CM = Pattern.compile("(\\d{1,4})(\\s?)(cm)");
	private static final double METERS_IN_FEET = .3048;
	private static final double METERS_IN_INCH = .0254;

	@Override
	@SuppressFBWarnings("DM_BOXED_PRIMITIVE_FOR_PARSING")
	public Integer process(String item) throws Exception {
		if (StringUtils.isBlank(item)) {
			return null;
		}

		Matcher feetInchMatcher = FEET_INCH.matcher(item);

		if (feetInchMatcher.find()) {
			double feet = Integer.valueOf(feetInchMatcher.group(1)) * METERS_IN_FEET * 100;
			double inch = Integer.valueOf(feetInchMatcher.group(3)) * METERS_IN_INCH * 100;
			return ((Long) Math.round(feet + inch)).intValue();
		}

		Matcher feetInchVerboseMatcher = FEET_INCH_VERBOSE.matcher(item);

		if (feetInchVerboseMatcher.find()) {
			double feet = Integer.valueOf(feetInchVerboseMatcher.group(1)) * METERS_IN_FEET * 100;
			return ((Long) Math.round(feet)).intValue();
		}

		Matcher cmMatcher = CM.matcher(item);

		if (cmMatcher.find()) {
			return Integer.valueOf(cmMatcher.group(1));
		}

		log.info("Could not extract height from not-empty value \"{}\"", item);
		return null;
	}
}
