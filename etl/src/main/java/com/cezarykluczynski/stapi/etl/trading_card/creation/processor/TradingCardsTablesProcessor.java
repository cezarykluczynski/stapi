package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.etl.trading_card.creation.service.TradingCardTableFilter;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.google.common.collect.Sets;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

@Service
public class TradingCardsTablesProcessor implements ItemProcessor<Elements, Set<TradingCard>> {

	private final TradingCardTableFilter tradingCardTableFilter;

	private final TradingCardsTableProcessor tradingCardsTableProcessor;

	@Inject
	public TradingCardsTablesProcessor(TradingCardTableFilter tradingCardTableFilter, TradingCardsTableProcessor tradingCardsTableProcessor) {
		this.tradingCardTableFilter = tradingCardTableFilter;
		this.tradingCardsTableProcessor = tradingCardsTableProcessor;
	}

	@Override
	public Set<TradingCard> process(Elements item) throws Exception {
		Set<TradingCard> tradingCardSet = Sets.newHashSet();

		if (item.isEmpty()) {
			return tradingCardSet;
		}

		for (Element element : item) {
			if (tradingCardTableFilter.isNonCardTable(element)) {
				continue;
			}

			tradingCardSet.addAll(tradingCardsTableProcessor.process(element));
		}

		return tradingCardSet;
	}

}
