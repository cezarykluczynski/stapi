package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.etl.trading_card.creation.service.TradingCardTableFilter;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.google.common.collect.Sets;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

@Service
public class TradingCardsTablesProcessor implements ItemProcessor<Elements, Set<TradingCardDeck>> {

	private final TradingCardTableFilter tradingCardTableFilter;

	private final TradingCardDecksTableProcessor tradingCardDecksTableProcessor;

	@Inject
	public TradingCardsTablesProcessor(TradingCardTableFilter tradingCardTableFilter, TradingCardDecksTableProcessor tradingCardDecksTableProcessor) {
		this.tradingCardTableFilter = tradingCardTableFilter;
		this.tradingCardDecksTableProcessor = tradingCardDecksTableProcessor;
	}

	@Override
	public Set<TradingCardDeck> process(Elements item) throws Exception {
		Set<TradingCardDeck> tradingCardDeckSet = Sets.newHashSet();

		if (item.isEmpty()) {
			return tradingCardDeckSet;
		}

		for (Element element : item) {
			if (tradingCardTableFilter.isNonCardTable(element)) {
				continue;
			}

			tradingCardDeckSet.addAll(tradingCardDecksTableProcessor.process(element));
		}

		return tradingCardDeckSet;
	}

}
