package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.deck;

import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card.TradingCardsProcessor;
import com.cezarykluczynski.stapi.etl.trading_card.creation.service.TradingCardTableFilter;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TradingCardDeckTableRowProcessor implements ItemProcessor<List<Element>, TradingCardDeck> {

	private static final String DOLLAR_SIGN = "$";
	private static final String LEFT_BRACKET = "(";
	private static final String RIGHT_BRACKET = ")";

	private final ElementTextNodesProcessor elementTextNodesProcessor;

	private final TradingCardTableFilter tradingCardTableFilter;

	private final TradingCardsProcessor tradingCardsProcessor;

	public TradingCardDeckTableRowProcessor(ElementTextNodesProcessor elementTextNodesProcessor, TradingCardTableFilter tradingCardTableFilter,
			TradingCardsProcessor tradingCardsProcessor) {
		this.tradingCardTableFilter = tradingCardTableFilter;
		this.elementTextNodesProcessor = elementTextNodesProcessor;
		this.tradingCardsProcessor = tradingCardsProcessor;
	}

	@Override
	public TradingCardDeck process(List<Element> item) throws Exception {
		if (item.isEmpty()) {
			return null;
		}

		Element header = item.get(0);
		List<String> headerTextNodes = elementTextNodesProcessor.process(header);

		if (headerTextNodes.stream().anyMatch(tradingCardTableFilter::isNonCardTable)) {
			return null;
		}

		List<String> nameCandidates = headerTextNodes.stream()
				.filter(this::isNotPrice)
				.collect(Collectors.toList());

		if (nameCandidates.isEmpty()) {
			log.warn("Could find name candidate for text nodes: {}", headerTextNodes);
			return null;
		}

		if (nameCandidates.size() > 1) {
			log.warn("More than one name candidate found: {}, using the first one", nameCandidates);
		}

		TradingCardDeck tradingCardDeck = new TradingCardDeck();

		String nameCandidate = nameCandidates.get(0);
		if (StringUtils.contains(nameCandidate, LEFT_BRACKET)) {
			String[] nameParts = StringUtils.split(nameCandidate, LEFT_BRACKET);
			tradingCardDeck.setName(StringUtils.trim(nameParts[0]));
			tradingCardDeck.setFrequency(StringUtils.strip(StringUtils.stripEnd(nameParts[1], RIGHT_BRACKET)));
		} else {
			tradingCardDeck.setName(nameCandidate);
		}

		tradingCardDeck.getTradingCards().addAll(tradingCardsProcessor.process(item.subList(1, item.size())));
		tradingCardDeck.getTradingCards().forEach(tradingCard -> tradingCard.setTradingCardDeck(tradingCardDeck));

		return tradingCardDeck;
	}

	private boolean isNotPrice(String headerTextNode) {
		return !StringUtils.startsWith(headerTextNode, DOLLAR_SIGN);
	}

}
