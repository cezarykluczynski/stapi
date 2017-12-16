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
public class IndividualWeightProcessor implements ItemProcessor<String, Integer> {

	private static final Pattern POUNDS = Pattern.compile("(\\d{1,4})(\\s?)((\\[{2})?pound|lb\\.|lbs).*");
	private static final Pattern KILOGRAMS = Pattern.compile("(\\d{1,4})(\\s?)(kg).*");
	private static final double POUNDS_IN_KILOGRAM = 2.20462262;

	@Override
	@SuppressFBWarnings("DM_BOXED_PRIMITIVE_FOR_PARSING")
	public Integer process(String item) throws Exception {
		if (StringUtils.isBlank(item)) {
			return null;
		}

		Matcher poundsMatcher = POUNDS.matcher(item);

		if (poundsMatcher.matches()) {
			double feet = Integer.valueOf(poundsMatcher.group(1)) / POUNDS_IN_KILOGRAM;
			return ((Long) Math.round(feet)).intValue();
		}

		Matcher kilogramMatcher = KILOGRAMS.matcher(item);

		if (kilogramMatcher.matches()) {
			return Integer.valueOf(kilogramMatcher.group(1));
		}

		log.info("Could not extract weight from not-empty value \"{}\"", item);
		return null;
	}

}
