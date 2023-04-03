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
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndividualWeightProcessor implements ItemProcessor<String, Integer> {

	private static final Pattern POUNDS = Pattern.compile("(?i)(\\d{1,4})(\\s?)((\\[{2})?pound|lb\\.|lbs).*");
	private static final Pattern KILOGRAMS = Pattern.compile("(?i)((\\.?|\\d){1,6})(\\s?)(kg|kilogram).*");
	private static final String POUNDS_IN_KILOGRAM = "2.20462262";
	private static final MathContext POUNDS_IN_KILOGRAM_MATH_CONTEXT = new MathContext(9, RoundingMode.HALF_UP);
	private static final String JSON_CONTENT_KEY = "content";

	private final WikitextApi wikitextApi;

	@Override
	@SuppressFBWarnings("DM_BOXED_PRIMITIVE_FOR_PARSING")
	public Integer process(String candidate) throws Exception {
		if (StringUtils.isBlank(candidate)) {
			return null;
		}

		String item = maybeGetJsonContent(candidate);
		item = wikitextApi.getWikitextWithoutLinks(item);
		Matcher poundsMatcher = POUNDS.matcher(item);

		if (poundsMatcher.matches()) {
			return new BigDecimal(poundsMatcher.group(1))
					.divide(new BigDecimal(POUNDS_IN_KILOGRAM), POUNDS_IN_KILOGRAM_MATH_CONTEXT)
					.setScale(0, RoundingMode.HALF_UP)
					.intValue();
		}

		Matcher kilogramMatcher = KILOGRAMS.matcher(item);

		if (kilogramMatcher.matches()) {
			return new BigDecimal(kilogramMatcher.group(1))
					.setScale(0, RoundingMode.HALF_UP)
					.intValue();
		}

		log.info("Could not extract weight from non-empty value \"{}\".", item);
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
