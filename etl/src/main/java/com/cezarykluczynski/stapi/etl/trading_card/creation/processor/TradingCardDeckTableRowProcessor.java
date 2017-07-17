package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import org.jsoup.nodes.Element;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradingCardDeckTableRowProcessor implements ItemProcessor<List<Element>, TradingCardDeck> {

	@Override
	public TradingCardDeck process(List<Element> item) throws Exception {
		// TODO
		return null;
	}

}
