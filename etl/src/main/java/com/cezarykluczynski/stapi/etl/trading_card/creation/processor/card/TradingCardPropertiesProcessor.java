package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card;

import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardProperties;
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.common.TradingCardItemsProcessor;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TradingCardPropertiesProcessor implements ItemProcessor<Element, TradingCardProperties> {

	private static final String DOLLAR_SIGN = "$";
	private static final String MADE = "Made)";
	private static final Pattern COPYRIGHT_PATTERN = Pattern.compile(".*?\\(©(\\d{4})\\)");
	private static final Pattern PRODUCTION_RUN_PATTERN = Pattern.compile(".*?\\(([0-9,]{1,6})\\s\\bMade\\b\\).*?");

	private final TradingCardItemsProcessor tradingCardItemsProcessor;

	public TradingCardPropertiesProcessor(TradingCardItemsProcessor tradingCardItemsProcessor) {
		this.tradingCardItemsProcessor = tradingCardItemsProcessor;
	}

	@Override
	public TradingCardProperties process(Element item) throws Exception {
		List<String> parts = getDescriptionParts(item);

		List<String> filteredParts = Lists.newArrayList();
		TradingCardProperties tradingCardProperties = new TradingCardProperties();

		for (String part : parts) {
			Matcher copyrightMatcher = COPYRIGHT_PATTERN.matcher(part);
			Matcher numberMadePattern = PRODUCTION_RUN_PATTERN.matcher(part);
			if (copyrightMatcher.matches()) {
				tradingCardProperties.setReleaseYear(Ints.tryParse(copyrightMatcher.group(1)));
			} else if (numberMadePattern.matches()) {
				tradingCardProperties.setProductionRun(tradingCardItemsProcessor.process(numberMadePattern.group(1)));
			} else if (!StringUtils.containsIgnoreCase(part, MADE)) {
				filteredParts.add(part);
			}
		}

		if (filteredParts.isEmpty()) {
			log.warn("Could not extract any properties from anchor {}", item);
			return null;
		} else if (filteredParts.size() == 1) {
			tradingCardProperties.setName(filteredParts.get(0));
		} else {
			tradingCardProperties.setNumber(filteredParts.get(0));
			tradingCardProperties.setName(filteredParts.get(1));

			if (filteredParts.size() > 2) {
				log.info("More than two lines find in trading card text elements: \"{}\"", filteredParts);
			}
		}

		return tradingCardProperties;
	}

	private List<String> getDescriptionParts(Element item) {
		return item.childNodes()
					.stream()
					.filter(node -> node instanceof TextNode)
					.map(node -> (TextNode) node)
					.map(TextNode::text)
					.filter(this::isNotPrice)
					.map(StringUtils::trim)
					.filter(StringUtils::isNotEmpty)
					.collect(Collectors.toList());
	}


	private boolean isNotPrice(String textNode) {
		return !StringUtils.startsWith(textNode, DOLLAR_SIGN);
	}

}
