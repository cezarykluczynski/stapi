package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndividualHeightProcessor implements ItemProcessor<String, Integer> {

	private static final Pattern FEET = Pattern.compile("(\\d{1,2})(\\s?)('|feet)");
	private static final Pattern FEET_INCH = Pattern.compile("(\\d{1,2})'(\\s?)((\\.?|\\d){1,5})(\"|'')");
	private static final Pattern FEET_INCH_VERBOSE = Pattern.compile("(\\d{1,2})(\\s?)(ft\\.)");
	private static final Pattern INCH = Pattern.compile("(\\d{1,2})(\"|'')");
	private static final Pattern CM = Pattern.compile("((\\.?|\\d){1,6})(\\s?)(cm)");
	private static final Pattern M = Pattern.compile("((\\.?|\\d){1,6})(\\s?)(m)");
	private static final double METERS_IN_FEET = .3048;
	private static final double METERS_IN_INCH = .0254;
	private static final String JSON_CONTENT_KEY = "content";

	private final WikitextApi wikitextApi;

	@Override
	@SuppressWarnings({"ReturnCount", "NPathComplexity"})
	@SuppressFBWarnings("DM_BOXED_PRIMITIVE_FOR_PARSING")
	public Integer process(String candidate) throws Exception {
		if (StringUtils.isBlank(candidate)) {
			return null;
		}

		String item = maybeGetJsonContent(candidate);
		item = wikitextApi.getWikitextWithoutLinks(item);

		Matcher feetInchMatcher = FEET_INCH.matcher(item);
		if (feetInchMatcher.find()) {
			double feet = Integer.valueOf(feetInchMatcher.group(1)) * METERS_IN_FEET * 100;
			double inch = new BigDecimal(feetInchMatcher.group(3))
					.multiply(BigDecimal.valueOf(METERS_IN_INCH))
					.multiply(BigDecimal.valueOf(100L))
					.doubleValue();
			return ((Long) Math.round(feet + inch)).intValue();
		}

		Matcher feetInchVerboseMatcher = FEET_INCH_VERBOSE.matcher(item);
		if (feetInchVerboseMatcher.find()) {
			double feet = Integer.valueOf(feetInchVerboseMatcher.group(1)) * METERS_IN_FEET * 100;
			return ((Long) Math.round(feet)).intValue();
		}

		Matcher feetMatcher = FEET.matcher(item);
		if (feetMatcher.find()) {
			double feet = Integer.valueOf(feetMatcher.group(1)) * METERS_IN_FEET * 100;
			return ((Long) Math.round(feet)).intValue();
		}

		Matcher inchMatcher = INCH.matcher(item);
		if (inchMatcher.find()) {
			double feet = Integer.valueOf(inchMatcher.group(1)) * METERS_IN_INCH * 100;
			return ((Long) Math.round(feet)).intValue();
		}

		Matcher cmMatcher = CM.matcher(item);
		if (cmMatcher.find()) {
			return new BigDecimal(cmMatcher.group(1))
					.setScale(0, RoundingMode.HALF_UP)
					.intValue();
		}

		Matcher meterMatcher = M.matcher(item);
		if (meterMatcher.find()) {
			return new BigDecimal(meterMatcher.group(1))
					.multiply(BigDecimal.valueOf(100L))
					.setScale(0, RoundingMode.HALF_UP)
					.intValue();
		}

		log.info("Could not extract height from non-empty value \"{}\"", item);
		return null;
	}

	private String maybeGetJsonContent(String item) {
		try {
			JSONObject jsonObject = new JSONObject(item);
			String content = jsonObject.has(JSON_CONTENT_KEY) ? (String) jsonObject.get(JSON_CONTENT_KEY) : null;
			if (StringUtils.isNotBlank(content)) {
				return content;
			}
		} catch (JSONException e) {
			// ignore and continue
		}
		return item;
	}

}
