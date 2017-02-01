package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.template.util.PatternDictionary;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DayInMonthProximityFindingProcessor implements ItemProcessor<Pair<String, PageLink>, Integer> {

	private static final Integer DAY_PROXIMITY_TOLERANCE = 6;

	private static final Pattern DAY_LEFT = Pattern.compile(PatternDictionary.DAY_GROUP + "(\\s?)$");

	private static final Pattern DAY_RIGHT = Pattern.compile("(\\s?)" + PatternDictionary.DAY_GROUP);

	@Override
	public Integer process(Pair<String, PageLink> item) throws Exception {
		if (item == null || item.getLeft() == null || item.getRight() == null) {
			return null;
		}

		String wikitext = item.getLeft();
		PageLink pageLink = item.getRight();
		String left = wikitext.substring(Math.max(0, pageLink.getStartPosition() - DAY_PROXIMITY_TOLERANCE), pageLink.getStartPosition());
		String right = wikitext.substring(pageLink.getEndPosition(), Math.min(wikitext.length(),
				pageLink.getEndPosition() + DAY_PROXIMITY_TOLERANCE));

		Matcher leftMatcher = DAY_LEFT.matcher(left);

		if (leftMatcher.matches()) {
			return Integer.valueOf(leftMatcher.group(1));
		}

		Matcher rightMathcer = DAY_RIGHT.matcher(right);
		if (rightMathcer.matches()) {
			return Integer.valueOf(rightMathcer.group(2));
		}

		return null;
	}

}
