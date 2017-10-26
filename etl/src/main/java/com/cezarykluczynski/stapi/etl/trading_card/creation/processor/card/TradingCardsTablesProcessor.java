package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card;

import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.deck.TradingCardDecksTableProcessor;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.google.common.collect.Sets;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TradingCardsTablesProcessor implements ItemProcessor<Elements, Set<TradingCardDeck>> {

	private final TradingCardDecksTableProcessor tradingCardDecksTableProcessor;

	public TradingCardsTablesProcessor(TradingCardDecksTableProcessor tradingCardDecksTableProcessor) {
		this.tradingCardDecksTableProcessor = tradingCardDecksTableProcessor;
	}

	@Override
	public Set<TradingCardDeck> process(Elements item) throws Exception {
		Set<TradingCardDeck> tradingCardDeckSet = Sets.newHashSet();

		if (item.isEmpty()) {
			return tradingCardDeckSet;
		}

		for (Element element : item) {
			tradingCardDeckSet.addAll(tradingCardDecksTableProcessor.process(element));
		}

		return tradingCardDeckSet;
	}

}
