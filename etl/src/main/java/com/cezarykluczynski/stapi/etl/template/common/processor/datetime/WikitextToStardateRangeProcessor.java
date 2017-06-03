package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WikitextToStardateRangeProcessor implements ItemProcessor<String, StardateRange> {

	private static final String BEGINS_ON = "begins on ";
	private static final String ENDS_ON = "ends on ";

	@Override
	public StardateRange process(String item) throws Exception {
		if (StringUtils.isEmpty(item)) {
			return null;
		}

		StardateRange stardateRangeFromSimpleParse = extractStardateRangeFromSimpleParse(item);
		StardateRange stardateRangeFromBeginsStardate = extractStardateRangeFromBeginsStardate(item);
		StardateRange stardateRangeFromEndsStardate = extractStardateRangeFromEndsStardate(item);
		StardateRange stardateRangeFromRangeStardate = extractStardateRangeFromRangeStardate(item);

		if (stardateRangeFromSimpleParse != null || stardateRangeFromBeginsStardate != null || stardateRangeFromEndsStardate != null
				|| stardateRangeFromRangeStardate != null) {
			return ObjectUtils.firstNonNull(stardateRangeFromSimpleParse, stardateRangeFromBeginsStardate, stardateRangeFromEndsStardate,
					stardateRangeFromRangeStardate);
		}

		return null;
	}

	private StardateRange extractStardateRangeFromSimpleParse(String item) {
		Float simpleParseResult = Floats.tryParse(item);
		return simpleParseResult == null ? null : StardateRange.of(simpleParseResult, simpleParseResult);
	}

	private StardateRange extractStardateRangeFromBeginsStardate(String item) {
		String itemLowerCase = StringUtils.lowerCase(item);

		if (itemLowerCase.startsWith(BEGINS_ON)) {
			Float beginsOnFloat = Floats.tryParse(itemLowerCase.substring(BEGINS_ON.length()));

			if (beginsOnFloat != null) {
				return StardateRange.of(beginsOnFloat, null);
			}
		}

		return null;
	}

	private StardateRange extractStardateRangeFromEndsStardate(String item) {
		String itemLowerCase = StringUtils.lowerCase(item);

		if (itemLowerCase.startsWith(ENDS_ON)) {
			Float endsOnFloat = Floats.tryParse(itemLowerCase.substring(ENDS_ON.length()));

			if (endsOnFloat != null) {
				return StardateRange.of(null, endsOnFloat);
			}
		}

		return null;
	}

	private StardateRange extractStardateRangeFromRangeStardate(String item) {
		List<String> possibleStardateList = Lists.newArrayList(item.split("-|\\sto\\s|/"))
				.stream()
				.map(StringUtils::trim)
				.collect(Collectors.toList());

		if (possibleStardateList.size() >= 2) {
			Float stardateFrom = Floats.tryParse(possibleStardateList.get(0));
			Float stardateTo = Floats.tryParse(possibleStardateList.get(possibleStardateList.size() - 1));

			if (stardateFrom != null && stardateTo != null) {
				return StardateRange.of(stardateFrom, stardateTo);
			}
		}

		return null;
	}

}
