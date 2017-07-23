package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card;

import com.cezarykluczynski.stapi.util.constant.AttributeName;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TradingCardItemNumberProcessor implements ItemProcessor<Element, Integer> {

	private static final Pattern ITEM_NUMBER_PATTERN = Pattern.compile(".*?\\bitem_number\\b(=)(\\d{1,10}).*?");

	@Override
	public Integer process(Element item) throws Exception {
		String href = item.attr(AttributeName.HREF);

		if (StringUtils.isEmpty(href)) {
			return null;
		}

		Matcher itemNumberMatcher = ITEM_NUMBER_PATTERN.matcher(href);

		if (itemNumberMatcher.matches()) {
			return Ints.tryParse(itemNumberMatcher.group(2));
		}

		return null;
	}

}
